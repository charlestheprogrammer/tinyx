import React from "react";
import "./styles/Account.scss";

import profilePicture from "../assets/pp.jpeg";

import { Link } from "react-router-dom";
import { getAllUsers, setUser } from "../api.js";

import { connect } from "../store/Account.js";
import { useDispatch } from "react-redux";

export default function Account() {
    const [users, setUsers] = React.useState([]);
    const [selectedUser, setSelectedUser] = React.useState(null);
    const [selectedUserIndex, setSelectedUserIndex] = React.useState(0);

    const dispatch = useDispatch();

    React.useEffect(() => {
        getAllUsers().then((res) => {
            setUsers(res.data);
        });
    }, []);

    React.useEffect(() => {
        if (users.length === 0) return;
        setSelectedUser(users[selectedUserIndex]);
        setUser(users[selectedUserIndex].id);
        dispatch(connect(users[selectedUserIndex]));
    }, [selectedUserIndex, users]);

    return (
        <div className="account">
            <Link to="/">
                <div className="navbar">
                    <svg
                        viewBox="0 0 24 24"
                        aria-hidden="true"
                        className="r-4qtqp9 r-yyyyoo r-dnmrzs r-bnwqim r-1plcrui r-lrvibr r-lrsllp r-1nao33i r-16y2uox r-8kz0gk"
                    >
                        <g>
                            <path d="M18.244 2.25h3.308l-7.227 8.26 8.502 11.24H16.17l-5.214-6.817L4.99 21.75H1.68l7.73-8.835L1.254 2.25H8.08l4.713 6.231zm-1.161 17.52h1.833L7.084 4.126H5.117z"></path>
                        </g>
                    </svg>
                </div>
            </Link>
            <div>
                <svg
                    viewBox="0 0 24 24"
                    aria-hidden="true"
                    onClick={() => {
                        setSelectedUserIndex(
                            (selectedUserIndex + 1) % users.length
                        );
                    }}
                    className="switch"
                >
                    <g>
                        <path d="M3 12c0-1.1.9-2 2-2s2 .9 2 2-.9 2-2 2-2-.9-2-2zm9 2c1.1 0 2-.9 2-2s-.9-2-2-2-2 .9-2 2 .9 2 2 2zm7 0c1.1 0 2-.9 2-2s-.9-2-2-2-2 .9-2 2 .9 2 2 2z"></path>
                    </g>
                </svg>
                <Link to={`/users/${selectedUser?.username ?? "username"}`}>
                    <div className="info">
                        <img
                            src={selectedUser?.imageUri ?? profilePicture}
                            alt=""
                        />
                    </div>
                </Link>
            </div>
        </div>
    );
}
