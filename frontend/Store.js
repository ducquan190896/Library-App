import {createStore, combineReducers, applyMiddleware } from "redux"
import { composeWithDevTools } from 'redux-devtools-extension';
import thunk from "redux-thunk"
import BookReducer from "./Reducers/BookReducer";
import CheckoutReducer from "./Reducers/CheckoutReducer";
import HistoryReducer from "./Reducers/HistoryReducer";
import MessageReducer from "./Reducers/MessageReducer";
import ReviewReducer from "./Reducers/ReviewReducer";
import UserReducer from "./Reducers/UserReducer";

const initalState = {}

const rootReducer = combineReducers({
Users: UserReducer,
Books: BookReducer,
Reviews: ReviewReducer,
Checkouts: CheckoutReducer,
Histories: HistoryReducer,
Messages: MessageReducer
})
const middleware = [thunk]

const Store = createStore( 
    rootReducer, 
    initalState,
   composeWithDevTools( applyMiddleware(...middleware))
)
export default Store;