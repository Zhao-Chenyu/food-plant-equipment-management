import React, { useEffect, useRef } from "react";
import { message } from "antd";

const FactoryMap = ({ selectedFactory, selectedToolName }) => {
  const containerRef = useRef(null);
  const colorMap = ["#1890ff", "#52c41a", "#fa8c16", "#f5222d", "#722ed1"]; // preset colors

  useEffect(() => {
    const container = containerRef.current;
    if (!container) return;

    const objectElement = container.querySelector("object");
    if (!objectElement) return;

    const handleLoad = () => {
      const svgDoc = objectElement.contentDocument;
      if (!svgDoc) return;

      // Reset all colors to gray
      svgDoc.querySelectorAll("circle, rect").forEach(el => {
        el.style.fill = "#ddd";
      });

      // Fetch attachment data
      fetch(`http://localhost:8080/api/tools/${selectedToolName}/attachments`)
        .then(res => res.json())
        .then(data => {
          const attachmentColorMap = {};
          let colorIndex = 0;

          Object.entries(data.attachmentsByProcess).forEach(([processId, attachment]) => {
            if (!attachmentColorMap[attachment.attachmentId]) {
              attachmentColorMap[attachment.attachmentId] = colorMap[colorIndex % colorMap.length];
              colorIndex++;
            }

            const target = svgDoc.getElementById(processId);
            if (target) {
              target.style.fill = attachmentColorMap[attachment.attachmentId];
              target.onclick = () => {
                message.info(`Attachment: ${attachment.fileName}`);
              };
            }
          });
        })
        .catch(() => message.error("Failed to load attachment information!"));
    };

    objectElement.addEventListener("load", handleLoad);
    return () => objectElement.removeEventListener("load", handleLoad);
  }, [selectedToolName, selectedFactory]);

  const factoryMapFiles = {
    "Salad Plant": "salad_plant_map.svg",
    "Vinegar Plant": "vinegar_plant_map.svg"
  };

  return (
    <div ref={containerRef} style={{ width: "100%", height: "100%" }}>
      <object
        type="image/svg+xml"
        data={`/images/${factoryMapFiles[selectedFactory]}`}
        width="100%"
        height="100%"
      >
        Failed to load map
      </object>
    </div>
  );
};

export default FactoryMap;
