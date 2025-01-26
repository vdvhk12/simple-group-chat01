"use client";

import { useRouter } from 'next/navigation';
import React, { useState, useRef, useEffect } from 'react';
import { useAuth } from '../context/AuthContext';
import { Client } from "@stomp/stompjs";
import SockJS from "sockjs-client";

export default function LoginPage() {
    const router = useRouter();
    const { setAuth, isLoggedIn } = useAuth();
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const [connected, setConnected] = useState(false);
    const stompClientRef = useRef<Client | null>(null);

    const connectWebSocket = (userId: string) => {
        const socket = new SockJS(`${process.env.NEXT_PUBLIC_API_URL}/ws/chat`);
        const client = new Client({
            webSocketFactory: () => socket,
            onConnect: () => {
                setConnected(true);
                console.log("WebSocket connected");
                client.subscribe(`/topic/messages/${userId}`, (message) => {
                    console.log("New message:", JSON.parse(message.body));
                });
            },
        });

        client.activate();
        stompClientRef.current = client;
    };

    const disconnectWebSocket = () => {
        stompClientRef.current?.deactivate();
        setConnected(false);
        console.log("WebSocket disconnected");
    };

    const handleLogin = async (e: React.FormEvent) => {
        e.preventDefault();

        try {
            const response = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/api/v1/auth/login`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ username, password }),
            });

            if (response.ok) {
                const data = await response.json();
                setAuth(data.id, data.username);
                connectWebSocket(data.id);
                router.push('/');
            } else {
                const data = await response.json();
                setError(response.status === 401 ? "아이디 또는 비밀번호가 잘못되었습니다." : data.message || '로그인에 실패했습니다.');
            }
        } catch (error) {
            setError('서버와의 연결에 실패했습니다.');
        }
    };

    useEffect(() => {
        if (isLoggedIn) {
            router.push('/');
        }
        return () => {
            stompClientRef.current?.deactivate();
        };
    }, [isLoggedIn]);

    return (
        <div className="flex justify-center items-center min-h-screen bg-gray-50">
            <div className="w-full max-w-md bg-white p-8 rounded-lg shadow-lg">
                <h2 className="text-2xl font-bold text-slate-800 text-center mb-6">로그인</h2>
                {error && <div className="text-red-500 text-sm text-center mb-4">{error}</div>}
                <form onSubmit={handleLogin} className="space-y-5">
                    <div>
                        <label htmlFor="email" className="block text-sm font-medium text-slate-700 mb-1">
                            Email
                        </label>
                        <input
                            type="email"
                            id="email"
                            className="w-full px-4 py-2 border border-gray-300 rounded-lg shadow-sm focus:outline-none focus:ring-2 focus:ring-indigo-500 text-gray-800"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                            required
                        />
                    </div>
                    <div>
                        <label htmlFor="password" className="block text-sm font-medium text-slate-700 mb-1">
                            Password
                        </label>
                        <input
                            type="password"
                            id="password"
                            className="w-full px-4 py-2 border border-gray-300 rounded-lg shadow-sm focus:outline-none focus:ring-2 focus:ring-indigo-500 text-gray-800"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                        />
                    </div>
                    <button
                        type="submit"
                        className="w-full py-2 bg-indigo-500 text-white rounded-lg shadow hover:bg-indigo-400 transition"
                    >
                        로그인
                    </button>
                </form>
            </div>
        </div>
    );
}
