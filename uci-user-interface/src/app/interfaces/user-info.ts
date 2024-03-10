import { ProfileResponse } from "./profile-response"

export interface UserInfo {
    "_id": string,
    "username": string,
    "password": string,
    "authorities": [
        {
            "_id": string,
            "authority": string,
            "permissions": [
                {
                    "_id": string,
                    "permission": string
                },
            ]
        }
    ],
    "permissions": [
        {
            "_id": string,
            "permission": string
        },
    ],
    "profile": ProfileResponse,
    "enabled": boolean,
    "accountNonLocked": boolean,
    "credentialsNonExpired": boolean,
    "accountNonExpired": boolean
}
