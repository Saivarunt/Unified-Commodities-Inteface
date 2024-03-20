export interface Pageable {
    "content": [],
    "pageable": {
        "pageNumber": number,
        "pageSize": number,
        "sort": {
            "unsorted": boolean,
            "empty": boolean,
            "sorted": boolean
        },
        "offset": number,
        "paged": boolean,
        "unpaged": boolean
    },
    "totalElements": number,
    "totalPages": number,
    "last": boolean,
    "size": number,
    "number": number,
    "sort": {
        "unsorted": boolean,
        "empty": boolean,
        "sorted": boolean
    },
    "first": boolean,
    "numberOfElements": number,
    "empty": boolean
}