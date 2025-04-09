// src/App.jsx

import React from "react";
import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import { Layout, Button } from "antd";
import Home from "./pages/Home";
import UploadPage from "./pages/UploadPage";
import UploadSuccess from "./pages/UploadSuccess";
import UploadRecords from "./pages/UploadRecords";
import ToolDetail from "./pages/ToolDetail";
import LoginPage from "./pages/LoginPage";
import { useAuth, AuthProvider } from "./context/AuthContext";

const { Header, Content, Footer } = Layout;

function AppContent() {
  const { isLoggedIn, logout } = useAuth();

  return (
    <Layout style={{ minHeight: "100vh" }}>
      <Header
        style={{
          backgroundColor: "#001529",
          color: "#fff",
          fontSize: "20px",
          display: "flex",
          justifyContent: "space-between",
          alignItems: "center",
          padding: "0 24px",
        }}
      >
        <div>{isLoggedIn ? "Tool Management Platform" : ""}</div>
        {isLoggedIn && (
          <Button type="link" onClick={logout} style={{ color: "#fff" }}>
            Log Out
          </Button>
        )}
      </Header>

      <Content style={{ background: "#f0f2f5", padding: "16px" }}>
        <Routes>
          <Route path="/login" element={<LoginPage />} />
          <Route path="/" element={isLoggedIn ? <Home /> : <Navigate to="/login" />} />
          <Route path="/upload" element={isLoggedIn ? <UploadPage /> : <Navigate to="/login" />} />
          <Route path="/upload-success" element={isLoggedIn ? <UploadSuccess /> : <Navigate to="/login" />} />
          <Route path="/records" element={isLoggedIn ? <UploadRecords /> : <Navigate to="/login" />} />
          <Route path="/tool/:id" element={isLoggedIn ? <ToolDetail /> : <Navigate to="/login" />} />
          <Route path="*" element={<Navigate to="/login" />} />
        </Routes>
      </Content>

      <Footer style={{ textAlign: "center", fontSize: "14px", color: "#888" }}>
        {isLoggedIn && (
          <>
            ©2025 Equipment Management ·{" "}
            <a
              href="https://github.com/Zhao-chenyu"
              target="_blank"
              rel="noopener noreferrer"
              style={{ color: "inherit", textDecoration: "none" }}
            >
              Chenyu Zhao
            </a>
          </>
        )}
      </Footer>
    </Layout>
  );
}

// Main App function, wrapped with AuthProvider for authentication context
function App() {
  return (
    <AuthProvider>
      <Router>
        <AppContent />
      </Router>
    </AuthProvider>
  );
}

export default App;
