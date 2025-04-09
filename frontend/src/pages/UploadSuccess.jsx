import React from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { Card, Button } from 'antd';

const UploadSuccess = () => {
  const location = useLocation();
  const navigate = useNavigate();

  // Extracting upload information from location state
  const {
    fileName,
    categoryId,
    remark,
    operations,
    uploadTime,
  } = location.state || {};

  // If no upload info is available, display a warning
  if (!fileName) {
    return <div style={{ padding: 20 }}>⚠️ No upload information found. Please start from the upload page.</div>;
  }

  return (
    <div style={{ maxWidth: 600, margin: '50px auto' }}>
      <Card title="✅ Upload Successful" bordered={false}>
        <p><strong>File Name:</strong> {fileName}</p>  {/* Display file name */}
        <p><strong>Tool Category:</strong> {categoryId}</p>  {/* Display tool category ID */}
        <p><strong>Remark:</strong> {remark}</p>  {/* Display remark */}
        <p><strong>Bound Operations:</strong> {operations?.join(', ') || 'None'}</p>  {/* Display bound operations */}
        <p><strong>Upload Time:</strong> {uploadTime || new Date().toLocaleString()}</p>  {/* Display upload time */}

        <div style={{ marginTop: 20 }}>
          <Button type="primary" onClick={() => navigate('/')}>Return to Homepage</Button>  {/* Button to navigate to homepage */}
          <Button style={{ marginLeft: 12 }} onClick={() => navigate('/upload')}>Continue Uploading</Button>  {/* Button to continue uploading */}
        </div>
      </Card>
    </div>
  );
};

export default UploadSuccess;
