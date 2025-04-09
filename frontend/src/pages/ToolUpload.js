// src/pages/ToolUpload.jsx

import React, { useState, useEffect } from "react";
import { AutoComplete, Button, Upload, Input, message, Modal, Select, Table } from "antd";
import FactoryMap from "../components/FactoryMap";
import { useLocation } from "react-router-dom";

const ToolUpload = () => {
  const location = useLocation();
  const [toolName, setToolName] = useState(location.state?.toolName || "");
  const [options, setOptions] = useState([]);
  const [selectedFactory, setSelectedFactory] = useState("New Factory");
  const [selectedTools, setSelectedTools] = useState([]);
  const [fileList, setFileList] = useState([]);
  const [remarks, setRemarks] = useState("");
  const [refreshKey, setRefreshKey] = useState(0);
  const [attachmentInfo, setAttachmentInfo] = useState(null);
  const [showModal, setShowModal] = useState(false);
  const [allProcesses, setAllProcesses] = useState([]);
  const [targetProcess, setTargetProcess] = useState(null);
  const [historyVisible, setHistoryVisible] = useState(false);
  const [historyData, setHistoryData] = useState([]);

  useEffect(() => {
    if (toolName) handleSearch(toolName);
  }, [toolName]);

  useEffect(() => {
    fetch("http://localhost:8080/api/all-processes")
      .then(res => res.json())
      .then(data => setAllProcesses(data));
  }, []);

  const handleSearch = async (value) => {
    if (!value) {
      setOptions([]);
      return;
    }
    try {
      const res = await fetch(`http://localhost:8080/api/tools/search?name=${value}`);
      const data = await res.json();
      setOptions(data.map((item) => ({ value: item.tool_name })));
    } catch (error) {
      console.error("Search failed:", error);
    }
  };

  const handleUpload = async () => {
    if (!toolName || selectedTools.length === 0 || fileList.length === 0) {
      message.error("Please complete the fields: Tool Name, Process, and Attachments!");
      return;
    }

    const formData = new FormData();
    formData.append("name", toolName);
    formData.append("factory", selectedFactory);
    formData.append("processes", selectedTools.join(","));
    formData.append("remarks", remarks);
    fileList.forEach((file) => formData.append("files", file.originFileObj));

    try {
      const res = await fetch("http://localhost:8080/api/tools/upload", {
        method: "POST",
        body: formData,
      });
      const result = await res.json();
      if (res.ok) {
        message.success("Upload successful: " + result.message);
        setToolName("");
        setSelectedTools([]);
        setFileList([]);
        setRemarks("");
        setRefreshKey((prev) => prev + 1);
      } else {
        throw new Error(result.error || "Upload failed");
      }
    } catch (error) {
      message.error("Upload failed: " + error.message);
    }
  };

  const handleMapClick = async (processId) => {
    if (!toolName) return;
    try {
      const res = await fetch(`http://localhost:8080/api/tools/${toolName}/attachments`);
      const data = await res.json();
      const attachment = data.attachmentsByProcess[processId];
      if (attachment) {
        setAttachmentInfo({ processId, ...attachment });
        setShowModal(true);
      }
    } catch (err) {
      console.error("Failed to retrieve attachment information", err);
    }
  };

  const handleCopy = async () => {
    if (!attachmentInfo || !targetProcess) return;
    try {
      const res = await fetch("http://localhost:8080/api/tools/copy-attachments", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          toolName,
          fromProcessId: attachmentInfo.processId,
          toProcessId: targetProcess,
          attachmentId: attachmentInfo.attachmentId,
        }),
      });
      const result = await res.json();
      if (res.ok) {
        message.success("Copy successful");
        setShowModal(false);
        setRefreshKey(prev => prev + 1);
      } else {
        throw new Error(result.error || "Copy failed");
      }
    } catch (err) {
      message.error("Copy failed: " + err.message);
    }
  };

  const openHistory = async () => {
    try {
      const res = await fetch(`http://localhost:8080/api/tools/${toolName}/history?processId=${attachmentInfo.processId}`);
      const data = await res.json();
      setHistoryData(data);
      setHistoryVisible(true);
    } catch (err) {
      message.error("Failed to retrieve history records");
    }
  };

  return (
    <div style={{ display: "flex", width: "100%", height: "100vh", padding: 20 }}>
      <div style={{ width: 400 }}>
        <AutoComplete
          options={options}
          style={{ width: "100%", marginBottom: 15 }}
          value={toolName}
          onChange={(value) => setToolName(value)}
          onSearch={handleSearch}
          placeholder="Enter or search tool name"
          allowClear
        />

        <Upload fileList={fileList} beforeUpload={() => false} multiple
          onChange={({ fileList }) => setFileList(fileList)}>
          <Button>Select Attachments</Button>
        </Upload>

        <Input.TextArea
          placeholder="Upload Remarks (optional)"
          value={remarks}
          onChange={(e) => setRemarks(e.target.value)}
          style={{ marginTop: 15 }}
        />

        <Button type="primary" onClick={handleUpload} style={{ marginTop: 15 }}>
          Upload
        </Button>
      </div>

      <div style={{ flex: 1 }}>
        <div style={{ marginBottom: 15 }}>
          {["New Factory", "Layered Factory", "Tempered Factory", "Edge Sealing Factory"].map((factory) => (
            <Button
              key={factory}
              type={selectedFactory === factory ? "primary" : "default"}
              onClick={() => setSelectedFactory(factory)}
              style={{ marginRight: 8 }}
            >
              {factory}
            </Button>
          ))}
        </div>

        <FactoryMap
          key={refreshKey}
          selectedFactory={selectedFactory}
          selectedTools={selectedTools}
          toolName={toolName}
          onClickProcess={handleMapClick}
        />
      </div>

      <Modal
        open={showModal}
        title="Attachment Details"
        onCancel={() => setShowModal(false)}
        footer={[
          <Button key="history" onClick={openHistory}>View History Versions</Button>,
          <Button key="copy" type="primary" onClick={handleCopy}>Copy to Process</Button>
        ]}
      >
        {attachmentInfo && (
          <div>
            <p><strong>Process:</strong> {attachmentInfo.processId}</p>
            <p><strong>File Name:</strong> {attachmentInfo.fileName}</p>
            <p><strong>Version:</strong> {attachmentInfo.attachmentId}</p>
            <Select
              style={{ width: "100%" }}
              placeholder="Select Target Process"
              value={targetProcess}
              onChange={setTargetProcess}
              options={allProcesses.map(proc => ({ value: proc.id, label: proc.name }))}
            />
          </div>
        )}
      </Modal>

      <Modal
        open={historyVisible}
        title="History Versions"
        onCancel={() => setHistoryVisible(false)}
        footer={null}
      >
        <Table
          rowKey="version"
          dataSource={historyData}
          pagination={false}
          columns={[
            { title: "Version", dataIndex: "version", width: 80 },
            { title: "File Name", dataIndex: "file_name" },
            { title: "Upload Time", dataIndex: "upload_time" }
          ]}
        />
      </Modal>
    </div>
  );
};

export default ToolUpload;
