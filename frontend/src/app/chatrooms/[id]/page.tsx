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
    const [hasMore, setHasMore] = useState<boolean>(true);
    const [page, setPage] = useState<number>(0);

    useEffect(() => {
        const fetchChatRoom = async () => {
            try {
                const response = await fetch(`http://localhost:8080/api/v1/chatrooms/${id}`);
                if (!response.ok) {
                    throw new Error("채팅방 정보를 불러오는 데 실패했습니다.");
                }
                const data = await response.json();
                setChatRoom(data);
            } catch (error) {
                console.error("Error:", error);
            }
        };

        const fetchMessages = async () => {
            try {
                const response = await fetch(
                    `http://localhost:8080/api/v1/chatrooms/messages/${id}?page=${page}&size=20`
                );
                if (!response.ok) {
                    throw new Error("메시지 목록을 불러오는 데 실패했습니다.");
                }
                const data = await response.json();
                setMessages((prevMessages) => [...prevMessages, ...data.content]);
                setHasMore(data.pageable.pageNumber < data.pageable.totalPages - 1);
            } catch (error) {
                console.error("Error:", error);
            } finally {
                setLoading(false);
            }
        };

        if (id) {
            fetchChatRoom();
            fetchMessages();
        }
    }, [id, page]);

    if (loading) {
        return <div className="text-center text-gray-800">로딩 중...</div>;
    }

    if (!chatRoom) {
        return <div className="text-center text-gray-800">채팅방 정보를 찾을 수 없습니다.</div>;
    }

    // `chatRoom.club`이 존재하는지 확인하고, 존재하지 않으면 fallback 텍스트를 출력
    const clubName = chatRoom.club ? chatRoom.club.name : "클럽 이름 없음";
    const clubDescription = chatRoom.club ? chatRoom.club.description : "설명이 없습니다.";

    const loadMoreMessages = () => {
        if (hasMore) {
            setPage((prevPage) => prevPage + 1);
        }
    };

    return (
        <div className="container mx-auto p-6">
            <h1 className="text-2xl font-bold mb-4 text-gray-800">{clubName}</h1>
            <p className="text-gray-700 mb-4">{clubDescription}</p>

            <h2 className="text-xl font-semibold text-gray-800 mb-3">메시지 목록</h2>
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

            {hasMore && (
                <button
                    onClick={loadMoreMessages}
                    className="mt-4 text-blue-600 hover:text-blue-800"
                >
                    더 보기
                </button>
            )}
        </div>
    );
};

export default ChatRoomDetail;
