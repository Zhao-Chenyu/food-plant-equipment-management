import React, { useEffect, useRef } from 'react';

const factoryMap = {
  'Salad Plant': '/images/salad_plant_map.svg',
  'Vinegar Plant': '/images/vinegar_plant_map.svg',
};

// Rules for selectable regions: not in disabledLabels and does not match specific suffix patterns
const isSelectable = (id, disabledLabels = []) => {
  const lower = id.toLowerCase();
  const isBySuffix = !/-(epbl|epbt|fbt|gt|coating)\d*$/i.test(lower);
  const isNotDisabled = !disabledLabels.map(d => d.toLowerCase()).includes(lower);
  return isBySuffix && isNotDisabled;
};

const SVGMap = ({
  selectedFactory,
  selectedOperations,
  setSelectedOperationsForFactory,
  disabledLabels = [],
}) => {
  const iframeRef = useRef();

  const attachClickHandler = (el, id) => {
    el.onclick = (e) => {
      e.preventDefault();
      e.stopPropagation();
      setSelectedOperationsForFactory((prev) => {
        const updated = prev.includes(id)
          ? prev.filter(op => op !== id)
          : [...prev, id];
        return updated;
      });
    };
  };

  const applyInteractivity = () => {
    const svgDoc = iframeRef.current?.contentDocument;
    if (!svgDoc) return;

    const svgRoot = svgDoc.querySelector('svg');
    if (svgRoot) {
      const existing = svgDoc.getElementById('watermark-text');
      if (!existing) {
        const watermark = svgDoc.createElementNS('http://www.w3.org/2000/svg', 'text');
        watermark.textContent = 'Food Plant Preview';
        watermark.setAttribute('id', 'watermark-text');
        watermark.setAttribute('x', '50%');
        watermark.setAttribute('y', '95%');
        watermark.setAttribute('text-anchor', 'middle');
        watermark.setAttribute('fill', 'gray');
        watermark.setAttribute('opacity', '0.2');
        watermark.setAttribute('font-size', '24px');
        watermark.setAttribute('pointer-events', 'none');
        svgRoot.appendChild(watermark);
      }
    }

    const clickable = svgDoc.querySelectorAll('[id]');
    clickable.forEach(el => {
      const id = el.id;
      if (!id || id.startsWith('__')) return;

      const tagName = el.tagName.toLowerCase();
      if (!['rect', 'g', 'path'].includes(tagName)) return;

      const freshEl = el.cloneNode(true);
      el.parentNode.replaceChild(freshEl, el);

      const freshId = freshEl.id;
      const selectable = isSelectable(freshId, disabledLabels);

      if (!selectable) {
        freshEl.style.cursor = 'not-allowed';
        freshEl.style.opacity = '0.3';
        freshEl.setAttribute('stroke', 'none');
        return;
      }

      const isSelected = selectedOperations.includes(freshId);
      freshEl.style.cursor = 'pointer';
      freshEl.setAttribute('stroke', isSelected ? 'red' : 'none');
      freshEl.setAttribute('stroke-width', isSelected ? '2' : '0');
      freshEl.style.opacity = '1';

      freshEl.onmouseenter = () => {
        if (!selectedOperations.includes(freshId)) {
          freshEl.style.opacity = '0.6';
        }
      };

      freshEl.onmouseleave = () => {
        if (!selectedOperations.includes(freshId)) {
          freshEl.style.opacity = '1';
        }
      };

      attachClickHandler(freshEl, freshId);
    });
  };

  useEffect(() => {
    const iframe = iframeRef.current;
    if (!iframe) return;
    iframe.addEventListener('load', applyInteractivity);
    return () => iframe.removeEventListener('load', applyInteractivity);
  }, [selectedFactory]);

  useEffect(() => {
    requestAnimationFrame(() => {
      applyInteractivity();
    });
  }, [selectedOperations, disabledLabels]);

  const svgPath = factoryMap[selectedFactory];
  if (!svgPath) return <div>Please select a factory to view the map</div>;

  return (
    <div style={{ width: '100%', height: '600px' }}>
      <iframe
        ref={iframeRef}
        src={svgPath}
        title="Factory Map"
        width="100%"
        height="100%"
        frameBorder="0"
        style={{ border: 'none' }}
      />
    </div>
  );
};

export default SVGMap;
