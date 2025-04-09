// src/pages/UploadPage.jsx

import React, { useState } from 'react';
import { Upload, Button, Input, message, Form, AutoComplete } from 'antd';
import { UploadOutlined } from '@ant-design/icons';
import axios from 'axios';
import SVGMap from '../components/SVGMap';

const UploadPage = () => {
  const [fileList, setFileList] = useState([]);
  const [categoryName, setCategoryName] = useState('');
  const [remark, setRemark] = useState('');
  const [selectedFactory, setSelectedFactory] = useState('Salad Plant');
  const [selectedOperations, setSelectedOperations] = useState([]);
  const [uploading, setUploading] = useState(false);

  // Handle file upload logic
  const handleUpload = async () => {
    const file = fileList[0]?.originFileObj;
    if (!categoryName || !remark || !file || selectedOperations.length === 0) {
      message.error('Please fill in all fields and select operations');
      return;
    }

    const formData = new FormData();
    formData.append('file', file);
    formData.append('categoryName', categoryName);
    formData.append('remark', remark);
    formData.append('operations', selectedOperations.join(','));

    try {
      setUploading(true);
      const res = await axios.post('http://localhost:8080/api/files/upload', formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
          username: encodeURIComponent(localStorage.getItem('username') || 'Test User'),
        },
      });
      message.success(`Upload successful, ID: ${res.data.id}`);
    } catch (err) {
      console.error('Upload failed', err);
      message.error(`Upload failed: ${err?.response?.data?.message || err.message}`);
    } finally {
      setUploading(false);
    }
  };

  const factoryList = ['Salad Plant', 'Vinegar Plant'];
  const disabledLabels = ['salad-salad1', 'salad-salad2', 'vinegar-vinegar1', 'vinegar-vinegar2'];

  return (
    <div style={{ display: 'flex', padding: 40 }}>
      <div style={{ width: '40%', paddingRight: 40 }}>
        <h2>Tool File Upload</h2>
        <Form layout="vertical">
          <Form.Item label="Tool Category Name">
            <AutoComplete value={categoryName} onChange={setCategoryName} placeholder="Enter category name" />
          </Form.Item>
          <Form.Item label="Remark">
            <Input.TextArea value={remark} onChange={(e) => setRemark(e.target.value)} />
          </Form.Item>
          <Form.Item label="Upload File">
            <Upload
              beforeUpload={() => false}
              onChange={({ fileList }) => setFileList(fileList)}
              fileList={fileList}
              onRemove={() => setFileList([])}
            >
              <Button icon={<UploadOutlined />}>Select File</Button>
            </Upload>
          </Form.Item>
          <Form.Item>
            <Button type="primary" onClick={handleUpload} loading={uploading}>
              Upload
            </Button>
          </Form.Item>
        </Form>
      </div>

      <div style={{ width: '60%' }}>
        <div style={{ marginBottom: 12 }}>
          {factoryList.map((name) => (
            <Button
              key={name}
              type={selectedFactory === name ? 'primary' : 'default'}
              onClick={() => setSelectedFactory(name)}
              style={{ marginRight: 8 }}
            >
              {name}
            </Button>
          ))}
        </div>
        <SVGMap
          key={selectedFactory + selectedOperations.join(',')}
          selectedFactory={selectedFactory}
          selectedOperations={selectedOperations}
          setSelectedOperationsForFactory={setSelectedOperations}
          disabledLabels={disabledLabels}
        />
      </div>
    </div>
  );
};

export default UploadPage;
