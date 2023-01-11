const initialState = {
    closeMessages: [],
    closeMessage: null,
    openMessages: [],
    openMessage: null,
    messageSuccess: false,
    messageError: false,
    message: null
}

export default (state = initialState, action) => {
    switch(action.type) {
        case "get_auth_open":
            return {
                ...state,
                messageSuccess: true,
                openMessages: action.payload
            }
        case "get_auth_close":
            return {
                ...state,
                messageSuccess: true,
                closeMessages: action.payload
            }
        case "get_admin_open":
            return {
                ...state,
                messageSuccess: true,
                openMessages: action.payload
            }
        case "get_admin_close":
                return {
                    ...state,
                    messageSuccess: true,
                    closeMessages: action.payload
                }
        case "add_question":
            return {
                ...state,
                messageSuccess: true,
                openMessages: state.openMessages.push(action.payload)
            }
        case "add_answer":
            return {
                ...state,
                messageSuccess: true,
                closeMessages: state.closeMessages.push(action.payload),
                openMessages: state.openMessages.filter(mess => mess.id != action.payload.id)
            }
        case "error_message":
            return {
                ...state,
                message: action.payload,
                messageError: true
            }
        case "reset_message":
            return {
                ...state,
                message: null,
                messageError: false,
                messageSuccess: false
            }
        default: 
            return state
    }
}