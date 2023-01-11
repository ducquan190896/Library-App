const initialState = {
    checkouts: [],
    checkout: null,
    checkoutSuccess: false,
    checkoutError: false,
    message: null
}

export default (state = initialState, action) => {
    switch(action.type) {
        case "get_checkouts_auth":
            return {
                ...state,
                checkouts: action.payload,
                checkoutSuccess: true
            }
        case "get_checkout":
            return {
                ...state,
                checkout: action.payload,
                checkoutSuccess: true
          
          }
        case "create_checkout":
            return {
                ...state,
                checkoutSuccess: true
          
          }
        case "return_book":
            return {
                ...state,
                checkoutSuccess: true,
                checkouts: state.checkouts.filter(checko => checko.id != action.payload)
            }
        case "extend_checkout":
            return {
                ...state,
                checkoutSuccess: true,
                checkouts: state.checkouts.map(checko => checko.id == action.payload.id ? action.payload : checko)
            }
        case "error_checkout":
            return {
                ...state,
                checkoutError: true,
                message: action.payload
            }
      
        case "reset_checkout":
            return {
                ...state,
                checkoutError: false,
                checkoutSuccess: false,
                message: null
               
            }
        default: 
        return state
    }
}