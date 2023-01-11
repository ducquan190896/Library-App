import AsyncStorage from "@react-native-async-storage/async-storage"

export const getAuthOpenMessages = () => async (dispatch, getState) => {
    try {
        const token = await AsyncStorage.getItem("token")
        console.log(token)
        const res = await fetch("http://10.0.2.2:8080/api/message/authUser/openMessages", {
            method: "GET",
            headers: {"Authorization": token}
        })
        const data = await res.json()
        console.log(data)
        dispatch({
            type: "get_auth_open",
            payload: data
        })

    } catch (err) {
        dispatch({
            type: "error_message",
            payload: err
        })
    }

}
export const getAdminOpenMessages = () => async (dispatch, getState) => {
    try {
        const token = await AsyncStorage.getItem("token")
        console.log(token)
        const res = await fetch("http://10.0.2.2:8080/api/message/openMessages", {
            method: "GET",
            headers: {"Authorization": token}
        })
        const data = await res.json()
        console.log(data)
        dispatch({
            type: "get_admin_open",
            payload: data
        })

    } catch (err) {
        dispatch({
            type: "error_message",
            payload: err
        })
    }

}

export const getAuthCloseMessages = () => async (dispatch, getState) => {
    try {
        const token = await AsyncStorage.getItem("token")
        console.log(token)
        const res = await fetch("http://10.0.2.2:8080/api/message/authUser/closedMessages", {
            method: "GET",
            headers: {"Authorization": token}
        })
        const data = await res.json()
        console.log(data)
        dispatch({
            type: "get_auth_close",
            payload: data
        })

    } catch (err) {
        dispatch({
            type: "error_message",
            payload: err
        })
    }

}
export const getAdminCloseMessages = () => async (dispatch, getState) => {
    try {
        const token = await AsyncStorage.getItem("token")
        console.log(token)
        const res = await fetch("http://10.0.2.2:8080/api/message/closedMessages", {
            method: "GET",
            headers: {"Authorization": token}
        })
        const data = await res.json()
        console.log(data)
        dispatch({
            type: "get_admin_close",
            payload: data
        })

    } catch (err) {
        dispatch({
            type: "error_message",
            payload: err
        })
    }

}
export const addQuestion = (question) => async (dispatch, getState) => {
    try {
        const token = await AsyncStorage.getItem("token")
        console.log(token)
        const res = await fetch("http://10.0.2.2:8080/api/message/addQuestion", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": token
            },
            body: JSON.stringify(question)
        })
        const data = await res.json()
        console.log(data)
        dispatch({
            type: "add_question",
            payload: data
        })

    } catch (err) {
        dispatch({
            type: "error_message",
            payload: err
        })
    }

}
export const addAnswer = (answer) => async (dispatch, getState) => {
    try {
        const token = await AsyncStorage.getItem("token")
        console.log(token)
        const res = await fetch("http://10.0.2.2:8080/api/message/addAnswer", {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
                "Authorization": token
            },
            body: JSON.stringify(answer)
        })
        const data = await res.json()
        console.log(data)
        dispatch({
            type: "add_answer",
            payload: data
        })

    } catch (err) {
        dispatch({
            type: "error_message",
            payload: err
        })
    }

}

export const resetMessage = () => (dispatch, getState) => {
    dispatch({
        type: "reset_message"
    })
}