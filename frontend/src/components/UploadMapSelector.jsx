import React, { useState } from 'react';
import './UploadMapSelector.css';

// Map factory names to corresponding SVG paths
const factoryMap = {
  'Salad Plant': '/images/salad_plant_map.svg',
  'Vinegar Plant': '/images/vinegar_plant_map.svg',
};

const UploadMapSelector = ({ selectedOperations, setSelectedOperations }) => {
  const [activeFactory, setActiveFactory] = useState('Salad Plant'); // Default factory

  // Toggle operation selection
  const handleClickBox = (factory, line, process) => {
    const operation = `${factory}-${line}-${process}`;
    const exists = selectedOperations.includes(operation);
    const newSelection = exists
      ? selectedOperations.filter(o => o !== operation)
      : [...selectedOperations, operation];
    setSelectedOperations(newSelection);
  };

  // Load and render the SVG map
  const renderSVG = () => {
    const svgPath = factoryMap[activeFactory];
    if (!svgPath) return <div>Please select a plant to display the map.</div>;

    return (
      <object
        type="image/svg+xml"
        data={svgPath}
        className="svg-map-object"
        onLoad={(e) => attachSVGListeners(e, activeFactory)}
      >
        Failed to load map.
      </object>
    );
  };

  // Add event listeners to rect elements in SVG
  const attachSVGListeners = (e, factory) => {
    const svgDoc = e.target.contentDocument;
    if (!svgDoc) return;

    const boxes = svgDoc.querySelectorAll('rect');
    boxes.forEach(box => {
      const label = box.getAttribute('id') || box.getAttribute('data-label') || box.getAttribute('name') || '';
      const [line = '', process = ''] = label.split('-');
      if (!line || !process) return;

      box.style.cursor = 'pointer';
      box.addEventListener('mouseenter', () => {
        box.setAttribute('opacity', 0.6);
      });
      box.addEventListener('mouseleave', () => {
        box.setAttribute('opacity', 1);
      });
      box.addEventListener('click', () => handleClickBox(factory, line, process));
    });
  };

  return (
    <div className="upload-map-container">
      {/* Factory switcher */}
      <div className="factory-switcher">
        {Object.keys(factoryMap).map(factory => (
          <button
            key={factory}
            className={activeFactory === factory ? 'active' : ''}
            onClick={() => setActiveFactory(factory)}
          >
            {factory}
          </button>
        ))}
      </div>

      {/* SVG map display */}
      <div className="svg-map-wrapper">
        {renderSVG()}
      </div>
    </div>
  );
};

export default UploadMapSelector;
