"use client";

import React, { useEffect, useState } from "react";
import { useParams } from "next/navigation";
import { ChatRoom } from "../types";

interface Message {
    id: string;
    senderId: number;
    senderName: string;
    content: string;
    createdAt: string;
}

const ChatRoomDetail: React.FC = () => {
    const { id } = useParams();
    const [chatRoom, setChatRoom] = useState<ChatRoom | null>(null);
    const [messages, setMessages] = useState<Message[]>([]);
    const [loading, setLoading] = useState<boolean>(true);

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
            } catch (error) {
                console.error("Error:", error);
            } finally {
                setLoading(false);
            }
        };

        fetchData();
    }, [id]);

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
                </ul>
            </div>

            <div className="mt-4 flex items-center space-x-4">
                <input
                    type="text"
                    className="flex-1 p-2 border border-gray-300 rounded-lg text-gray-800"
                    placeholder="메시지를 입력하세요"
                />
                <button className="bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700">
                    보내기
                </button>
            </div>
        </div>
    );
};

export default ChatRoomDetail;