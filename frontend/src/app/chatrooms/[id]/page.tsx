"use client";

import React, {useEffect, useState, useRef, useContext} from "react";
import { useParams } from "next/navigation";
import { useAuth } from "../../context/AuthContext";
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { ChatRoom } from "../types";

interface Message {
    id?: string;
    senderId: number;
    senderName: string;
    content: string;
    createdAt: string;
}

const ChatRoomDetail: React.FC = () => {
    const { id } = useParams();
    const { userId } = useAuth();
    const { username } = useAuth();
    const [chatRoom, setChatRoom] = useState<ChatRoom | null>(null);
    const [messages, setMessages] = useState<Message[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [newMessage, setNewMessage] = useState<string>('');
    const clientRef = useRef<Client | null>(null);
    const messagesEndRef = useRef<HTMLDivElement>(null);

    const scrollToBottom = () => {
        messagesEndRef.current?.scrollIntoView({ behavior: "smooth" });
    };

    useEffect(() => {
        scrollToBottom();
    }, [messages]);

    useEffect(() => {
        if (!id) return;

        const fetchData = async () => {
            try {
                const [chatRoomResponse, messagesResponse] = await Promise.all([
                    fetch(`http://localhost:8080/api/v1/chatrooms/${id}`),
                    fetch(`http://localhost:8080/api/v1/chatrooms/${id}/messages?page=0&size=20`)
                ]);

                if (!chatRoomResponse.ok || !messagesResponse.ok) {
                    throw new Error("데이터를 불러오는 데 실패했습니다.");
                }

                const chatRoomData = await chatRoomResponse.json();
                const messagesData = await messagesResponse.json();

                setChatRoom(chatRoomData);
                setMessages(messagesData.content);
                setLoading(false);

                connectWebSocket(chatRoomData.id);
            } catch (error) {
                console.error("Error:", error);
                setLoading(false);
            }
        };

        fetchData();

        return () => {
            if (clientRef.current) {
                clientRef.current.deactivate();
            }
        };
    }, [id]);

    const connectWebSocket = (chatRoomId: string) => {
        const socket = new SockJS('http://localhost:8080/ws/chat');
        const client = new Client({
            webSocketFactory: () => socket,
            onConnect: () => {
                // 구독 경로 수정
                client.subscribe(`/topic/chatroom/${chatRoomId}`, (message) => {
                    const receivedMessage: Message = JSON.parse(message.body);
                    setMessages((prevMessages) => [...prevMessages, receivedMessage]);
                });
                console.log(`웹소켓 연결 성공 -> chatRoom: ${chatRoomId}`);
            },
            onStompError: (frame) => {
                console.error('Broker reported error: ' + frame.headers['message']);
                console.error('Additional details: ' + frame.body);
            }
        });

        client.activate();
        clientRef.current = client;
    };

    const sendMessage = () => {
        if (!newMessage.trim() || !clientRef.current) return;

        const messageToSend = {
            chatRoomId: id,
            senderId: userId,
            senderName: username,
            content: newMessage,
        };

        // 메시지 전송 경로 수정
        clientRef.current.publish({
            destination: `/app/chat/${id}`,
            body: JSON.stringify(messageToSend)
        });

        setNewMessage('');
    };

    if (loading) {
        return <div className="text-center text-gray-800">로딩 중...</div>;
    }

    if (!chatRoom) {
        return <div className="text-center text-gray-800">채팅방 정보를 찾을 수 없습니다.</div>;
    }

    const clubName = chatRoom.club ? chatRoom.club.name : "클럽 이름 없음";

    return (
        <div className="container mx-auto p-6 bg-white rounded-lg shadow-lg max-w-4xl">
            <div className="flex flex-col items-center mb-6">
                <h1 className="text-3xl font-semibold text-gray-900">{clubName}</h1>
            </div>

            <div className="bg-gray-100 p-4 rounded-lg border border-gray-300 h-96 overflow-y-auto">
                <ul className="space-y-4">
                    {messages.map((message) => (
                        <li key={message.id} className="border-b border-gray-300 pb-3 mb-3">
                            <div className="flex justify-between items-center">
                                <p className="font-semibold text-gray-800">{message.senderName}</p>
                                <p className="text-sm text-gray-500">{new Date(message.createdAt).toLocaleString()}</p>
                            </div>
                            <p className="text-gray-700">{message.content}</p>
                        </li>
                    ))}
                    <div ref={messagesEndRef} />
                </ul>
            </div>

            <div className="mt-4 flex items-center space-x-4">
                <input
                    type="text"
                    value={newMessage}
                    onChange={(e) => setNewMessage(e.target.value)}
                    onKeyPress={(e) => e.key === 'Enter' && sendMessage()}
                    className="flex-1 p-2 border border-gray-300 rounded-lg text-gray-800"
                    placeholder="메시지를 입력하세요"
                />
                <button
                    onClick={sendMessage}
                    className="bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700"
                >
                    보내기
                </button>
            </div>
        </div>
    );
};

export default ChatRoomDetail;