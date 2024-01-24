import React from "react";

import profilePicture from "../assets/pp.jpeg";
import "./styles/WritePost.scss";

export default function WritePost() {
    return (
        <div className="writePost">
            <img src={profilePicture} alt="" />
            <div className="content">
                <textarea
                    name=""
                    id=""
                    placeholder="What is happening?!"
                ></textarea>
                <div className="actions">
                    <div className="icons">
                        <div>
                            <svg
                                viewBox="0 0 24 24"
                                aria-hidden="true"
                            >
                                <g>
                                    <path d="M3 5.5C3 4.119 4.119 3 5.5 3h13C19.881 3 21 4.119 21 5.5v13c0 1.381-1.119 2.5-2.5 2.5h-13C4.119 21 3 19.881 3 18.5v-13zM5.5 5c-.276 0-.5.224-.5.5v9.086l3-3 3 3 5-5 3 3V5.5c0-.276-.224-.5-.5-.5h-13zM19 15.414l-3-3-5 5-3-3-3 3V18.5c0 .276.224.5.5.5h13c.276 0 .5-.224.5-.5v-3.086zM9.75 7C8.784 7 8 7.784 8 8.75s.784 1.75 1.75 1.75 1.75-.784 1.75-1.75S10.716 7 9.75 7z"></path>
                                </g>
                            </svg>
                        </div>
                        <div>
                            <svg
                                viewBox="0 0 24 24"
                                aria-hidden="true"
                            >
                                <g>
                                    <path d="M4.5 3.88l4.432 4.14-1.364 1.46L5.5 7.55V16c0 1.1.896 2 2 2H13v2H7.5c-2.209 0-4-1.79-4-4V7.55L1.432 9.48.068 8.02 4.5 3.88zM16.5 6H11V4h5.5c2.209 0 4 1.79 4 4v8.45l2.068-1.93 1.364 1.46-4.432 4.14-4.432-4.14 1.364-1.46 2.068 1.93V8c0-1.1-.896-2-2-2z"></path>
                                </g>
                            </svg>
                        </div>
                    </div>
                    <button>Post</button>
                </div>
            </div>
        </div>
    );
}
