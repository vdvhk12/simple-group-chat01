"use client";

import Link from 'next/link';
import { useAuth } from '../context/AuthContext';  // 로그인 상태를 가져오기 위한 import
import { useRouter } from 'next/navigation';  // 라우터를 사용하기 위한 import

const NavigationBar = () => {
    const { userId, username, logout } = useAuth();  // 로그인 상태와 logout 함수 가져오기
    const router = useRouter();

    const handleLogout = () => {
        logout();
        router.push('/');  // 로그아웃 후 메인 페이지로 이동
    };

    return (
        <nav className="flex space-x-4 items-center">
            {userId ? (
                <>
                    <span className="text-white">{username}님</span>
                    <Link
                        href="/chatroom"
                        className="hover:text-indigo-300 transition"
                    >
                        채팅방 목록
                    </Link>
                    <button
                        onClick={handleLogout}
                        className="hover:text-indigo-300 transition"
                    >
                        로그아웃
                    </button>
                </>
            ) : (
                <>
                    <Link
                        href="/login"
                        className="hover:text-indigo-300 transition"
                    >
                        로그인
                    </Link>
                    <Link
                        href="/signup"
                        className="hover:text-indigo-300 transition"
                    >
                        회원가입
                    </Link>
                </>
            )}
        </nav>
    );
};

export default NavigationBar;
