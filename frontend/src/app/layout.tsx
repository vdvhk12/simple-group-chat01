import "./globals.css";
import Link from "next/link";
import { ReactNode } from "react";

export default function RootLayout({ children }: { children: ReactNode }) {
    return (
        <html lang="ko">
        <body className="bg-gray-50">
        <nav className="bg-slate-800 text-white p-4 shadow-md">
            <div className="container mx-auto flex justify-between items-center">
                <Link href="/" className="text-xl font-semibold text-indigo-400 hover:text-indigo-300 transition">
                    MyApp
                </Link>
                <div className="flex space-x-4">
                    <Link href="/login" className="hover:text-indigo-300 transition">
                        로그인
                    </Link>
                    <Link href="/signup" className="hover:text-indigo-300 transition">
                        회원가입
                    </Link>
                </div>
            </div>
        </nav>
        <main className="container mx-auto p-6">{children}</main>
        </body>
        </html>
    );
}
