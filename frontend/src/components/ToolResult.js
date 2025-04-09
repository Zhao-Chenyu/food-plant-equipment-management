import React from "react";
import { Button } from "antd";
import { Link } from "react-router-dom";

const ToolResult = ({ searchResults, selectedTools = [], onSelect }) => {
  const handleClick = (toolName) => {
    if (selectedTools.includes(toolName)) {
      onSelect(selectedTools.filter((name) => name !== toolName));
    } else {
      onSelect([...selectedTools, toolName]);
    }
  };

  return (
    <div style={{ padding: "10px", border: "1px solid #ddd", borderRadius: "5px", background: "#f9f9f9" }}>
      <h3>Search Results</h3>
      {Array.isArray(searchResults) && searchResults.length === 0 ? (
        <p>No matching tools found</p>
      ) : (
        <ul style={{ listStyle: "none", padding: 0 }}>
          {Array.isArray(searchResults) && searchResults.map((tool) => (
            <li
              key={tool.tool_name}
              style={{
                padding: "8px",
                display: "flex",
                alignItems: "center",
                justifyContent: "space-between",
                background: selectedTools.includes(tool.tool_name) ? "#007bff" : "#fff",
                color: selectedTools.includes(tool.tool_name) ? "#fff" : "#000",
                border: "1px solid #ddd",
                marginBottom: "5px",
                borderRadius: "5px",
                cursor: "pointer"
              }}
              onClick={() => handleClick(tool.tool_name)}
            >
              <span>{tool.tool_name}</span>
              <Link to="/upload" state={{ toolName: tool.tool_name }}>
                <Button size="small" style={{ marginLeft: 10 }}>
                  Upload
                </Button>
              </Link>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default ToolResult;
