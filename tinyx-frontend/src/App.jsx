import React from "react";

import { BrowserRouter as Router, Routes, Route } from "react-router-dom";

import "./App.css";
import User from "./components/User";
import Feed from "./components/Feed";
import Account from "./components/Account";
import Search from "./components/Search";

function App() {
    return (
        <Router>
            <div className="overlay">
                <Account />
                <Routes>
                    <Route path="/users/:user" Component={User} />
                    <Route path="/" Component={Feed} />
                </Routes>
                <Search />
            </div>
        </Router>
    );
}

export default App;
