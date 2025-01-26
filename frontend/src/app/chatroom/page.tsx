"use client";

import React from "react";
import ChatRoomList from "./ChatRoomList";
import { useAuth } from "../context/AuthContext";

function Page() {
    const { userId } = useAuth();

    if (!userId) {
        return <div className="text-center text-gray-500">로그인이 필요합니다.</div>;
    }

    return (
        <div className="container mx-auto p-4">
            <h1 className="text-2xl font-bold mb-4">채팅방 목록</h1>
            <ChatRoomList memberId={userId} />
        </div>
    );
}

export default Page;
