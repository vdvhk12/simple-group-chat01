"use client";

import React, { createContext, useContext, useState, useEffect } from 'react';

interface AuthContextType {
    userId: number | null;
    username: string | null;
    setAuth: (id: number, username: string) => void;
    logout: () => void;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider = ({ children }: { children: React.ReactNode }) => {
    const [userId, setUserId] = useState<number | null>(null);
    const [username, setUsername] = useState<string | null>(null);

    // 앱 시작 시 로컬 스토리지에서 인증 상태 복원
    useEffect(() => {
        const storedUserId = localStorage.getItem('userId');
        const storedUsername = localStorage.getItem('username');

        if (storedUserId && storedUsername) {
            setUserId(Number(storedUserId));
            setUsername(storedUsername);
        }
    }, []);

    const setAuth = (id: number, username: string) => {
        setUserId(id);
        setUsername(username);
        localStorage.setItem('userId', id.toString());
        localStorage.setItem('username', username);
    };

    const logout = () => {
        setUserId(null);
        setUsername(null);
        localStorage.removeItem('userId');
        localStorage.removeItem('username');
    };

    return (
        <AuthContext.Provider value={{ userId, username, setAuth, logout }}>
            {children}
        </AuthContext.Provider>
    );
};

export const useAuth = () => {
    const context = useContext(AuthContext);
    if (!context) {
        throw new Error('useAuth must be used within an AuthProvider');
    }
    return context;
};