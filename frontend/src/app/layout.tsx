"use client";

import './globals.css';
import Link from 'next/link';
import { ReactNode } from 'react';
import { AuthProvider } from './context/AuthContext';
import NavigationBar from "@/app/components/NavigationBar";

export default function RootLayout({ children }: { children: ReactNode }) {
    return (
        <html lang="ko">
        <body className="bg-gray-50">
        <AuthProvider>
            <nav className="bg-slate-800 text-white p-4 shadow-md">
                <div className="container mx-auto flex justify-between items-center">
                    <Link
                        href="/"
                        className="text-xl font-semibold text-indigo-400 hover:text-indigo-300 transition"
                    >
                        MyApp
                    </Link>
                    <div className="flex space-x-4">
                        <NavigationBar />
                    </div>
                </div>
            </nav>
            <main className="container mx-auto p-6">{children}</main>
        </AuthProvider>
        </body>
        </html>
    );
}
