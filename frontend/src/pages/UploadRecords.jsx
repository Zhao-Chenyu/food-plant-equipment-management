// src/pages/UploadRecords.jsx

import React, { useEffect, useState } from 'react';
import { Table, Tag, Spin, message } from 'antd';
import axios from 'axios';

const UploadRecords = () => {
  const [records, setRecords] = useState([]);
  const [loading, setLoading] = useState(true);

  // Fetch upload records on component mount
  useEffect(() => {
    axios.get('/api/files/records')
      .then(res => setRecords(res.data))
      .catch(err => message.error('Failed to fetch upload records'))
      .finally(() => setLoading(false));
  }, []);

  const columns = [
    {
      title: 'File Name',  // Column title for file name
      dataIndex: 'fileName',
      key: 'fileName',
    },
    {
      title: 'Tool Category ID',  // Column title for tool category ID
      dataIndex: 'categoryId',
      key: 'categoryId',
    },
    {
      title: 'Uploader',  // Column title for uploader name
      dataIndex: 'uploaderName',
      key: 'uploaderName',
    },
    {
      title: 'Bound Operations',  // Column title for operations
      dataIndex: 'operations',
      key: 'operations',
      render: (ops) => (
        <>{ops.map(op => <Tag key={op}>{op}</Tag>)}</>  // Display operations as tags
      ),
    },
    {
      title: 'Upload Time',  // Column title for upload time
      dataIndex: 'uploadTime',
      key: 'uploadTime',
    }
  ];

  return (
    <div style={{ padding: 32 }}>
      <h2>📄 Upload Records</h2>  // Page title
      <Spin spinning={loading}>
        <Table
          rowKey="id"
          columns={columns}
          dataSource={records}
          pagination={{ pageSize: 10 }}
        />
      </Spin>
    </div>
  );
};

export default UploadRecords;
