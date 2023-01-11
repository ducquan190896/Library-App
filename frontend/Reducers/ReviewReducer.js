const initialState = {
    reviews: [],
    review: null,
    reviewSuccess: false,
    reviewError: false,
    message: null
}

export default (state = initialState, action) => {
    switch(action.type) {
        case "get_all_reviews":
            return {
                ...state,
                reviews: action.payload,
                reviewSuccess: true
            }
        case "get_reviews_Book":
            return {
                ...state,
                reviews: action.payload,
                reviewSuccess: true
            }
        case "create_review":
            return {
                ...state,
                reviews: state.reviews.push(action.payload),
                review: action.payload,
                reviewSuccess: action.payload
            }
        case "error_review":
            return {
                ...state,
                reviewError: true,
                message: action.payload
            }
        case "reset_review":
            return {
                ...state,
                reviewError: false,
                reviewSuccess: false,
                message: null
            }
        default:
            return state
    }
}