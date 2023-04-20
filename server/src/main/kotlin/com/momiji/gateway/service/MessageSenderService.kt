package com.momiji.gateway.service

import com.momiji.api.common.model.ChatAdminsResponse
import com.momiji.api.common.model.ResponseStatus
import com.momiji.api.common.model.SendMessageRequest
import com.momiji.api.common.model.SendMessageResponse
import com.momiji.api.common.model.SimpleResponse
import com.momiji.api.frontend.FrontendContainer
import com.momiji.api.gateway.outbound.model.FrontendNamesResponse
import com.momiji.api.gateway.outbound.model.SendTextMessageRequest
import com.momiji.gateway.repository.entity.MessageEntity
import com.momiji.gateway.repository.entity.UserEntity
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


    fun sendText(request: SendTextMessageRequest): SendMessageResponse {
        logger.debug("Sending text message: $request")
        try {
            val sentMessage = frontendContainer.sendTextMessage(
                body = SendMessageRequest(
                    text = request.text,
                    replyTo = request.replyToMessageId,
                    chatId = request.chatId,
                ),
                frontend = request.frontend,
            )

            if (sentMessage.status != ResponseStatus.OK) {
                return SendMessageResponse(
                    errorMessage = sentMessage.errorMessage,
                    status = sentMessage.status,
                )
            }

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
                    nativeId = sentMessage.messageId!!
                )
            )

            return SendMessageResponse(
                messageId = sentMessage.messageId,
                status = ResponseStatus.OK,
            )
        } catch (ex: Exception) {
            logger.error("An exception has occurred!", ex)

            return SendMessageResponse(
                errorMessage = ex.toString(),
                messageId = null,
                status = ResponseStatus.INTERNAL_SERVER_ERROR,
            )
        }
    }

    fun sendTypingAction(frontend: String, chatId: String): SimpleResponse {
        logger.debug("Sending typing action to frontend \"$frontend\", chat id \"$chatId\"")

        frontendContainer.sendTypingAction(chatId = chatId, frontend = frontend)

        return SimpleResponse(
            status = ResponseStatus.OK,
        )
    }

    fun getChatAdmins(frontend: String, chatId: String): ChatAdminsResponse {
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
