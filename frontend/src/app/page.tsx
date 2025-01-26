export default function MainPage() {
    return (
        <div className="flex flex-col items-center justify-center min-h-screen bg-gray-50">
            <h1 className="text-5xl font-bold text-slate-800 mb-4">Welcome to MyApp</h1>
            <p className="text-lg text-gray-600 mb-6 text-center">
                Discover amazing features and a community waiting for you!
            </p>
            <button className="px-6 py-3 bg-indigo-500 text-white rounded-lg shadow hover:bg-indigo-400 transition">
                Get Started
            </button>
        </div>
    );
}
