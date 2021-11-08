package com.simple.tweet.data

import com.thedeanda.lorem.Lorem
import com.thedeanda.lorem.LoremIpsum
import java.time.Instant
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.random.Random


object TweetsApi {
    private const val TWEETS_COUNT = 100

    private val nlOrSpaceSeparator: (Random) -> String = { random ->
        ("\n".takeIf { random.nextBoolean() } ?: " ")
    }
    private val lorem: Lorem = LoremIpsum.getInstance()
    val list: List<Tweet>

    init {
        list = generateSequence {
            val id = UUID.randomUUID().toString()
            val seed = id.hashCode()
            val rnd = Random(seed)

            val withRetweet = rnd.nextBoolean()

            val timeCreatedAt = createdAt(rnd)
            val tweetUser = User.random(rnd)
            val tweetPoll = generatePoll(rnd)
            val tweetText = generateText(rnd, tweetPoll != null)
            val tweetImages = if (tweetPoll != null) emptyList() else generateImageUrls()

            val messages = rnd.nextInt(0, 30)
            val comments = generateComments(rnd, messages)
            val tweet = Tweet(
                id = id,
                userNickname = tweetUser.nickname,
                userFullName = tweetUser.fullName,
                userAvatar = tweetUser.avatarUrl,
                text = tweetText,
                images = tweetImages,
                likes = rnd.nextInt(0, 1000),
                retweets = rnd.nextInt(0, 750),
                messages = messages,
                comments = comments,
                createdAt = timeCreatedAt.toEpochMilli(),
                retweet = null,
                poll = tweetPoll
            )

            if (withRetweet) {
                var retweetTime = timeCreatedAt.plusMillis(
                    TimeUnit.HOURS.toMillis(rnd.nextLong(0L, 5L))
                )
                if (retweetTime.isAfter(Instant.now())) {
                    retweetTime = Instant.now()
                }
                val retweetUser = User.random(rnd)
                val retweet = Tweet(
                    id = UUID.randomUUID().toString(),
                    userNickname = retweetUser.nickname,
                    userFullName = retweetUser.fullName,
                    userAvatar = retweetUser.avatarUrl,
                    text = generateText(rnd, compactMessage = true),
                    images = emptyList(),
                    likes = rnd.nextInt(0, 100),
                    retweets = rnd.nextInt(0, 200),
                    messages = 0,
                    comments = emptyList(),
                    createdAt = retweetTime.toEpochMilli(),
                    retweet = tweet,
                    poll = null
                )
                retweet
            } else {
                tweet
            }
        }.take(TWEETS_COUNT).toList()
    }

    private fun createdAt(rnd: Random) = Instant.now().minusMillis(
        TimeUnit.HOURS.toMillis(rnd.nextLong(0L, 24L))
    )

    private fun generateComments(rnd: Random, count: Int): List<Tweet> {
        return if (count == 0) {
            emptyList()
        } else {
            generateSequence {
                val user = User.random(rnd)
                val id = UUID.randomUUID().toString()
                val tweet = Tweet(
                    id = id,
                    userNickname = user.nickname,
                    userFullName = user.fullName,
                    userAvatar = user.avatarUrl,
                    text = generateText(rnd, compactMessage = rnd.nextBoolean(), withEmojis = true),
                    images = emptyList(),
                    likes = 0,
                    retweets = 0,
                    messages = 0,
                    comments = emptyList(),
                    createdAt = createdAt(rnd).toEpochMilli(),
                    retweet = null,
                    poll = null
                )
                tweet
            }.take(count).toList()
        }
    }

    private fun generatePoll(rnd: Random): Poll? {
        val createPoll = rnd.nextBoolean() && Random(System.nanoTime()).nextBoolean()
        return if (createPoll) {
            var totalVotes = 0
            val pollSize = rnd.nextInt(3, 6)
            val positions = (1..pollSize)
                .map {
                    val voted = rnd.nextInt(0, 500)
                    totalVotes += voted
                    PollPosition(
                        text = generateText(rnd, compactMessage = true, withEmojis = false),
                        voted = voted
                    )
                }
                .toList()
            Poll(
                isCompleted = rnd.nextInt() % 2 == 0,
                totalVotes = totalVotes,
                positions = positions
            )
        } else {
            null
        }
    }

    private fun generateText(rnd: Random, compactMessage: Boolean = false, withEmojis: Boolean = true): String {
        return buildString {
            val textWithEmoji = withEmojis && rnd.nextBoolean()
            val message = if (compactMessage) lorem.getWords(1, 6) else lorem.getParagraphs(1, 2)
            if (textWithEmoji) {
                val mostlyEmojiText = rnd.nextBoolean()
                if (mostlyEmojiText) {
                    repeat(
                        /*lines*/ if (compactMessage) 1 else rnd.nextInt(from = 2, until = 12)
                    ) {
                        if (rnd.nextBoolean()) {
                            append(Emojis.getRandomEmojis(rnd.nextInt(2, 3)) + " ")
                        } else {
                            append(lorem.getWords(1, 3) + " ")
                        }
                        append(" ")
                        append(lorem.getWords(1, 2) + " ")
                        append(nlOrSpaceSeparator.invoke(rnd))
                        append(Emojis.getRandomEmojis(rnd.nextInt(1, 3)))
                        append(" ")
                    }
                    return@buildString
                }

                val prefixEmojis = Emojis.getRandomEmojis(
                    count = rnd.nextInt(1, 4)
                ) + nlOrSpaceSeparator(rnd)
                val suffixEmojis = Emojis.getRandomEmojis(
                    count = rnd.nextInt(0, 4)
                ) + nlOrSpaceSeparator(rnd)

                append(prefixEmojis)
                append(message)
                append(suffixEmojis)
            } else {
                append(message)
            }
        }
    }

    private fun randomAvatarUrl(rnd: Random, gender: Gender): String {
        return "https://randomuser.me/api/portraits/${gender.name.lowercase()}/${rnd.nextInt(1, 99)}.jpg"
    }

    private fun generateImageUrls(): List<String> {
        val rnd = Random(System.nanoTime())
        return if (rnd.nextBoolean()) {
            val imgCount = rnd.nextInt(1, 7)
            generateSequence {
                val imgId = rnd.nextInt(0, 1000)
                "https://picsum.photos/seed/${imgId}/1280/720"
            }.take(imgCount).toList()
        } else {
            emptyList()
        }
    }

    private fun randomColor(rnd: Random): String {
        val nextInt: Int = rnd.nextInt(0xffffff + 1)
        // format it as hexadecimal string (with hashtag and leading zeros)
        return String.format("%06x", nextInt)
    }

    private enum class Gender {
        MEN, WOMEN
    }

    /*
    * val tweetUserGender = if (rnd.nextBoolean()) Gender.WOMEN else Gender.MEN
            val tweetUserFullName = when (tweetUserGender) {
                Gender.MEN -> lorem.nameMale
                Gender.WOMEN -> lorem.nameFemale
            }
            val nickname = lorem.getWords(1).lowercase().replace(" ", "").take(6)
    * */
    private data class User(
        val gender: Gender,
        val nickname: String,
        val fullName: String,
        val avatarUrl: String
    ) {
        companion object {
            fun random(rnd: Random): User {
                val userGender = if (rnd.nextBoolean()) Gender.WOMEN else Gender.MEN
                val userFullName = when (userGender) {
                    Gender.MEN -> lorem.nameMale
                    Gender.WOMEN -> lorem.nameFemale
                }
                val nickname = lorem.getWords(1).lowercase().replace(" ", "").take(6)
                val userAvatar = randomAvatarUrl(rnd, userGender)
                return User(
                    gender = userGender,
                    nickname = "@${nickname}",
                    fullName = userFullName,
                    avatarUrl = userAvatar
                )
            }
        }
    }
}