// src/pages/ToolDetail.jsx

import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { Descriptions, Button, Spin, message } from 'antd';
import axios from 'axios';

const ToolDetail = () => {
  const { id } = useParams(); // Extract the tool ID from URL parameters
  const navigate = useNavigate(); // Hook for navigation
  const [detail, setDetail] = useState(null); // State to store tool details
  const [loading, setLoading] = useState(true); // State to handle loading status

  useEffect(() => {
    // Fetch tool details from the API
    axios.get(`/api/files/detail/${id}`)
      .then(res => setDetail(res.data)) // Set the tool detail on success
      .catch(() => message.error('Failed to fetch details')) // Show error message on failure
      .finally(() => setLoading(false)); // Set loading to false once data is fetched
  }, [id]);

  if (loading) return <Spin style={{ margin: 80 }} />; // Show loading spinner while fetching data
  if (!detail) return <div style={{ padding: 32 }}>No data found for this tool.</div>; // Display message if no tool detail found

  return (
    <div style={{ padding: 32 }}>
      <h2>📌 Tool Details</h2> {/* Title for tool details section */}
      <Descriptions bordered column={1} size="middle">
        {/* Display various details of the tool */}
        <Descriptions.Item label="Tool Category ID">{detail.categoryId}</Descriptions.Item>
        <Descriptions.Item label="File Name">{detail.fileName}</Descriptions.Item>
        <Descriptions.Item label="Modification Remarks">{detail.remark}</Descriptions.Item>
        <Descriptions.Item label="Uploader">{detail.uploaderName}</Descriptions.Item>
        <Descriptions.Item label="Upload Time">{detail.uploadTime}</Descriptions.Item>
        <Descriptions.Item label="Bound Operations">{detail.operations?.join(', ')}</Descriptions.Item>
        <Descriptions.Item label="Download Link">
          {/* Link to download the file */}
          <a href={detail.fileUrl} target="_blank" rel="noopener noreferrer">Click to download</a>
        </Descriptions.Item>
      </Descriptions>

      {/* Button to navigate back to the upload records page */}
      <Button style={{ marginTop: 20 }} onClick={() => navigate('/records')}>
        Return to Upload Records
      </Button>
    </div>
  );
};

export default ToolDetail;
