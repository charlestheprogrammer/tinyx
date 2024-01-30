import { configureStore } from "@reduxjs/toolkit";
import accountSlice from "./Account";

export const store = configureStore({
    reducer: {
        account: accountSlice,
    },
});
