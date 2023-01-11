const initialState = {
    book: null,
    books: [],
    bookSuccess: false,
    bookError: false,
    messagse: null,
    updatedBook: null,
    updateStatus: false
}

export default (state = initialState, action) =>  {
    switch(action.type) {
        case "get_all_books":
            return {
                ...state,
                books: action.payload,
                bookSuccess: true
            }
        case "get_book":
            return {
                ...state,
                book: action.payload,
                bookSuccess: true
            }
        
        case "increase_book": 
            return {
                ...state,
                books: state.books.map(bo => bo.id == action.payload.id ? action.payload : bo),
                book: action.payload,
                bookSuccess: true
            }
        case "decrease_book": 
            return {
                ...state,
                books: state.books.map(bo => bo.id == action.payload.id ? action.payload : bo),
                book: action.payload,
                bookSuccess: true
            }
        case "add_book":
            return {
                ...state,
                bookSuccess: true
            }
        case "book_error":
            return {
                ...state,
                bookError: true,
                messagse: action.payload
            }
        case "reset_book":
            return {
                ...state,
                bookSuccess: false,
                bookError: false,
                messagse: null,
                updateStatus: false
            }
        default: 
            return state
    }
}