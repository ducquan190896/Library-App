export const loadBooks = () => async (dispatch, getState) => {
    try {
        const res = await fetch("http://10.0.2.2:8080/api/books/all")
       
        const data =  await res.json()
        console.log(data)

        dispatch({
            type: "get_all_books",
            payload: data
        })

    } catch (err) {
        dispatch({
            type: "book_error",
            payload: "err"
        })
    }
}
export const loadBookById = (id) => async (dispatch, getState) => {
    try {
        const res = await fetch(`http://10.0.2.2:8080/api/books/id/${id}`)
        const data = res.json()
        console.log(data)

        dispatch({
            type: "get_book",
            payload: data
        })

    } catch (err) {
        dispatch({
            type: "book_error",
            payload: "err"
        })
    }
}

export const resetBooks = () => (dispatch, getState) => {
    dispatch({
        type: "reset_book"
    })
}