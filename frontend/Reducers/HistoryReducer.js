const initialState = {
    histories: [],
    history: null,
    historySuccess: false,
    historyError: false,
    message: null
}

export default (state = initialState, action) => {
    switch(action.type) {
        case "get_histories_auth":
            return {
                ...state,
                histories: action.payload,
                historySuccess: true
            }
        case "error_history":
            return {
                ...state,
                historyError: true,
                message: action.payload
            }
        case "reset_history":
            return {
                ...state,
                historyError: false,
                historySuccess: false,
                message: null
            }
        default: 
         return state
    }
}