// src/hooks/useAuth.js

import { useState, useEffect } from 'react';

export function useAuth() {
  const [username, setUsername] = useState(() => localStorage.getItem('username'));
  const isLoggedIn = !!username;

  const login = (name) => {
    localStorage.setItem('username', name);
    setUsername(name);
  };

  const logout = () => {
    localStorage.removeItem('username');
    setUsername(null);
  };

  useEffect(() => {
    const stored = localStorage.getItem('username');
    if (stored && !username) {
      setUsername(stored);
    }
  }, [username]);

  return { username, isLoggedIn, login, logout };
}
