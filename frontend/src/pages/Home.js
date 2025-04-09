import React, { useState, useEffect } from "react";
import { Input, Button } from "antd";
import FactoryMap from "../components/FactoryMap";
import ToolResult from "../components/ToolResult";
import { Link } from "react-router-dom";

const Home = () => {
  const [searchTerm, setSearchTerm] = useState("");
  const [searchResults, setSearchResults] = useState([]);
  const [selectedFactory, setSelectedFactory] = useState("Salad Plant");
  const [selectedTools, setSelectedTools] = useState([]);

  const handleSearch = async () => {
    console.log("Search is disabled.");
  };

  useEffect(() => {
    setSelectedTools([]);
  }, [selectedFactory]);

  const factories = ["Salad Plant", "Vinegar Plant"];

  return (
    <div style={{ display: "flex", width: "100%", height: "100vh" }}>
      <div style={{ width: 400, padding: 20 }}>
        <Input
          placeholder="Search for tool name"
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
          style={{ marginBottom: 20 }}
        />

        <Button type="primary" style={{ marginRight: 10 }} disabled>
          Search
        </Button>

        <Link to="/upload">
          <Button>Upload</Button>
        </Link>

        <div style={{ marginTop: 15 }}>
          {factories.map(factory => (
            <Button
              key={factory}
              type={selectedFactory === factory ? "primary" : "default"}
              onClick={() => setSelectedFactory(factory)}
              style={{ marginRight: 8, marginBottom: 8 }}
            >
              {factory}
            </Button>
          ))}
        </div>

        <ToolResult
          searchResults={searchResults}
          selectedTools={selectedTools}
          onSelect={setSelectedTools}
        />
      </div>

      <div style={{ flex: 1 }}>
        <FactoryMap
          selectedFactory={selectedFactory}
          selectedToolName={selectedTools.length > 0 ? selectedTools[0]?.name : ""}
        />
      </div>
    </div>
  );
};

export default Home;
