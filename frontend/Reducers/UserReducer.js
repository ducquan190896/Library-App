const initialState = {
    user: null,
    userSuccess: false,
    userError: false,
    message: null
}

export default (state = initialState, action ) => {
    switch (action.type) {
        case "login":
            return {
                ...state,
                user: action.payload,
                userSuccess: true
            }
        case "register":
            return {
                 ...state,
                 user: action.payload,
                 userSuccess: true
                }
        case "logout":
            return {
                ...state,
                user: null,
                userSuccess: true
            }
        case "user_error":
            return {
                ...state,
                userError: true,
                message: action.payload
            }
        default:
            return state;
    }
}