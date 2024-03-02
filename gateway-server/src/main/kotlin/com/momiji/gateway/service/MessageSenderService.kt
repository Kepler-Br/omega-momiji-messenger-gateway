package com.momiji.gateway.service

import com.momiji.api.common.model.ResponseStatus
import com.momiji.api.common.model.SimpleResponse
import com.momiji.api.frontend.FrontendContainer
import com.momiji.api.frontend.model.ChatAdminsFrontendResponse
import com.momiji.api.frontend.model.SendMessageFrontendResponse
import com.momiji.api.gateway.outbound.model.FrontendNamesResponse
import com.momiji.api.gateway.outbound.model.SendBinaryMessageGatewayRequest
import com.momiji.api.gateway.outbound.model.SendTextMessageRequest
import com.momiji.gateway.repository.entity.MessageEntity
import com.momiji.gateway.repository.entity.UserEntity
import com.momiji.gateway.repository.entity.enumerator.MediaType
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service


@Service
class MessageSenderService(
    private val frontendContainer: FrontendContainer,
    private val dataService: DataService,
) {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)


    private fun getSelfUser(frontend: String): UserEntity {
        return UserEntity(
            username = "SELF",
            fullname = "SELF",
            nativeId = "SELF",
            frontend = frontend,
        )
    }

    fun sendVoice(request: SendBinaryMessageGatewayRequest): SendMessageFrontendResponse {
        logger.debug(
            "Sending voice message: " +
                    "frontend: \"{}\"; " +
                    "chatId: \"{}\"; " +
                    "reply to: \"{}\"; " +
                    "base64 data len: \"{}\"",
            request.frontend, request.chatId, request.replyToMessageId, request.data.length
        )

        try {
            val sentMessageId = frontendContainer
                .withFrontend(request.frontend)
                .sendImage(
                    data = request.data,
                    replyTo = request.replyToMessageId,
                    chatId = request.chatId,
                )

            val selfUser = dataService.createOrUpdateUser(getSelfUser(request.frontend))

            val chat = dataService.createDefaultOrGetChat(
                nativeId = request.chatId,
                frontend = request.frontend,
            )

            dataService.updateOrSaveNewMessage(
                MessageEntity(
                    mediaType = MediaType.VOICE,
                    chatId = chat.id,
                    userId = selfUser.id,
                    replyToMessageNativeId = request.replyToMessageId,
                    frontend = request.frontend,
                    nativeId = sentMessageId
                )
            )

            return SendMessageFrontendResponse(
                messageId = sentMessageId,
                status = ResponseStatus.OK,
            )
        } catch (ex: Exception) {
            logger.error("An exception has occurred!", ex)

            return SendMessageFrontendResponse(
                errorMessage = ex.toString(),
                messageId = null,
                status = ResponseStatus.INTERNAL_SERVER_ERROR,
            )
        }
    }

    fun sendImage(request: SendBinaryMessageGatewayRequest): SendMessageFrontendResponse {
        logger.debug(
            "Sending image message: " +
                    "frontend: \"{}\"; " +
                    "chatId: \"{}\"; " +
                    "reply to: \"{}\"; " +
                    "base64 data len: \"{}\"",
            request.frontend, request.chatId, request.replyToMessageId, request.data.length
        )

        try {
            val sentMessageId = frontendContainer
                .withFrontend(request.frontend)
                .sendImage(
                    data = request.data,
                    replyTo = request.replyToMessageId,
                    chatId = request.chatId,
                )

            val selfUser = dataService.createOrUpdateUser(getSelfUser(request.frontend))

            val chat = dataService.createDefaultOrGetChat(
                nativeId = request.chatId,
                frontend = request.frontend,
            )

            dataService.updateOrSaveNewMessage(
                MessageEntity(
                    mediaType = MediaType.PHOTO,
                    chatId = chat.id,
                    userId = selfUser.id,
                    replyToMessageNativeId = request.replyToMessageId,
                    frontend = request.frontend,
                    nativeId = sentMessageId
                )
            )

            return SendMessageFrontendResponse(
                messageId = sentMessageId,
                status = ResponseStatus.OK,
            )
        } catch (ex: Exception) {
            logger.error("An exception has occurred!", ex)

            return SendMessageFrontendResponse(
                errorMessage = ex.toString(),
                messageId = null,
                status = ResponseStatus.INTERNAL_SERVER_ERROR,
            )
        }
    }

    fun sendText(request: SendTextMessageRequest): SendMessageFrontendResponse {
        logger.debug("Sending text message: {}", request)
        try {
            val sentMessageId = frontendContainer
                .withFrontend(request.frontend)
                .sendText(
                    text = request.text,
                    replyTo = request.replyToMessageId,
                    chatId = request.chatId,
                )

            val selfUser = dataService.createOrUpdateUser(getSelfUser(request.frontend))

            val chat = dataService.createDefaultOrGetChat(
                nativeId = request.chatId,
                frontend = request.frontend,
            )

            dataService.updateOrSaveNewMessage(
                MessageEntity(
                    text = request.text,
                    chatId = chat.id,
                    userId = selfUser.id,
                    replyToMessageNativeId = request.replyToMessageId,
                    frontend = request.frontend,
                    nativeId = sentMessageId
                )
            )

            return SendMessageFrontendResponse(
                messageId = sentMessageId,
                status = ResponseStatus.OK,
            )
        } catch (ex: Exception) {
            logger.error("An exception has occurred!", ex)

            return SendMessageFrontendResponse(
                errorMessage = ex.toString(),
                messageId = null,
                status = ResponseStatus.INTERNAL_SERVER_ERROR,
            )
        }
    }

    fun sendTypingAction(frontend: String, chatId: String): SimpleResponse {
        logger.debug("Sending typing action to frontend \"$frontend\", chat id \"$chatId\"")

        frontendContainer.withFrontend(frontend).sendTypingAction(chatId = chatId)

        return SimpleResponse(
            status = ResponseStatus.OK,
        )
    }

    fun getChatAdmins(frontend: String, chatId: String): ChatAdminsFrontendResponse {
        return frontendContainer.getFrontend(frontend).getChatAdmins(chatId)
    }

    fun getFrontendNames(): FrontendNamesResponse {
        logger.debug("Get frontend names")

        return FrontendNamesResponse(
            names = frontendContainer.getFrontendNames(),
            status = ResponseStatus.OK
        )
    }

}
