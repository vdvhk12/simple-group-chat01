// types.ts
export interface Member {
    memberId: number;
    username: string;
    role: 'MANAGER' | 'MEMBER';
}

export interface Club {
    id: number;
    name: string;
    description: string;
    creator: string;
    members: Member[];
}

export interface ChatRoom {
    id: number;
    club: Club;
}
