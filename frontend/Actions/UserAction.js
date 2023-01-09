import AsyncStorage from "@react-native-async-storage/async-storage";


export const UserLogin = (form) => async (dispatch, getState)  => {
    try {
  const response = await fetch("http://10.0.2.2:8080/login", {
            method: "PUT",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(form)
        })
        // await AsyncStorage.setItem("authorization", response.headers["Authorization"])
        console.log(response.headers["Authorization"])
        const data = await response.json();
        console.log(data)
        dispatch({
            type:"login",
            payload: data
        })

    } catch (err) {
        dispatch({
            type: "user_error",
            payload: err
        })
    }
}
export const UserRegister = (form) => async (dispatch, getState)  => {
    try {

        const response = await fetch("http://10.0.2.2:8080/api/users/register", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(form)
        })


        await AsyncStorage.setItem("authorization", response.headers["Authorization"])
        console.log(response.headers["Authorization"])
        const data = await response.json();
        console.log(data)
        dispatch({
            type:"login",
            payload: data
        })

    } catch (err) {
        dispatch({
            type: "user_error",
            payload: err
        })
    }
}

export const changePasswordFunction = (form) => async (dispatch, getState)  => {
    try {

      
        
        const response = await fetch("http://localhost:8080/api/users/changepassword", {
            method: "POST",
            headers: {"Content-Type": "application/json"},        
            body: JSON.stringify(form)
        })
        const data = await response.json();
        console.log(data)
        dispatch({
            type:"login",
            payload: data
        })

    } catch (err) {
        dispatch({
            type: "user_error",
            payload: err
        })
    }
}