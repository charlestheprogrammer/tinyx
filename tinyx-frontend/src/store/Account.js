import { createSlice } from "@reduxjs/toolkit";

const initialState = {
    id: null,
    username: null,
    imageUri: null,
    bannerUri: null,
};

export const accountSlice = createSlice({
    name: "counter",
    initialState,
    reducers: {
        connect: (state, action) => {
            const account = action.payload;
            state.id = account.id;
            state.username = account.username;
            state.imageUri = account.imageUri;
            state.bannerUri = account.bannerUri;
        },
    },
});

export const { connect } = accountSlice.actions;

// The function below is called a selector and allows us to select a value from
// the state. Selectors can also be defined inline where they're used instead of
// in the slice file. For example: `useSelector((state: RootState) => state.counter.value)`
export const selectAccountId = (state) => state.account.id;

// We can also write thunks by hand, which may contain both sync and async logic.
// Here's an example of conditionally dispatching actions based on current state.

export default accountSlice.reducer;
