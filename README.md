# TinyX



## Services

| Service            | Port | URL                   |
|--------------------|------|-----------------------|
| repo-post          | 9001 | http://localhost:9001 |
| repo-social        | 9002 | http://localhost:9002 |
| srvc-home-timeline | 9003 | http://localhost:9003 |
| srvc-search        | 9004 | http://localhost:9004 |
| srvc-user-timeline | 9005 | http://localhost:9005 |

## Features

### repo-post
- [ ] User can create a post
- [ ] Post can contain text, a media, a repost. At least one of, at most two of
- [ ] Post can be a reply to another post
- [ ] Reposts and replies need to point to an existing post at the time of creation
- [ ] Reposts and replies cannot point to a post from a blocked user at the time of creation
- [ ] User can delete its own post
- [ ] Can query a user’s posts
- [ ] Can query a specific post
- [ ] Can query a specific post’s reply

### repo-social
- [ ] User can like/unlike a post from a user who doesn’t have a block relationship with them
- [ ] Likes should be dated to allow proper sorting in timeline services (currently using the
post’s post date instead)
- [ ] User can follow/unfollow a user who doesn’t have a block relationship with them
- [ ] User can block/unblock a user
- [ ] Blocking a user removes follow relations between these users (asynchronously ?)
- [ ] Can query a post’s liking users
- [ ] Can query a user’s liked posts
- [ ] Can query a user’s follows
- [ ] Can query a user’s followers
- [ ] Can query a user’s block list
- [ ] Can query the list of users who blocked a user

### srvc-search
- [ ] Created posts are indexed
- [ ] Deleted posts are removed from the index
- [ ] If regular words are in the search terms, results must contain AT LEAST ONE of the
searched words (vague search).
- [ ] If hashtags are in the search terms, results must include ALL of the searched hashtags (strict
search)
- [ ] If both words and hashtags are in the search terms, results must fulfill BOTH rules above
at once
- [ ] A hashtag word should not be matched as a regular word, only as a hashtag (e.g. searching
“word” should not find “#word”)

### srvc-user-timeline
- [ ] Get a list of posts related to a specific user, containing
- [ ] Posts authored by the user
- [ ] Posts liked by the user (It is currently inferred that if a post’s authorID does not match
the timeline’s ID it means the post is liked)
- [ ] Sorted in chronological order (in case of likes, the date of like is used, not the date of
post post)
- [ ] Asynchronously update the timeline whenever a post is created/deleted/liked/unliked for
said user

### srvc-home-timeline
- [ ] Get a list of posts related to a specific user’s follows, containing
- [ ] posts authored by users followed by the user (bonus: second-degree follows ?)
- [ ] posts liked by users followed by the user (bonus: second-degree follows ?)
- [ ] Timeline must contain the information of which user liked the post as we cannot
just rely on the timeline owner’s ID this time
- [ ] Sorted in chronological order (in case of likes, the date of like is used, not the date of
post post)
- [ ] Duplicates are allowed (if multiple users like the same post)
- [ ] Asynchronously update the timeline whenever a post is created/deleted/liked/unliked for
users followed by the user
- [ ] Asynchronously update the timeline whenever a user is followed/unfollowed by the user
Essentially a merging of all user timelines of users followed by the querying user