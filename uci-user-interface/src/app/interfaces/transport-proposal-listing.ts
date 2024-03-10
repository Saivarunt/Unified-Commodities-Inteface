import { ProfileResponse } from "./profile-response";

export interface TransportProposalListing {
    _id: string,
    request_id: string,
    profile: ProfileResponse,
    status: string
}
