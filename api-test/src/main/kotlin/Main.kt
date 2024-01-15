package com.padoru

import it.auties.whatsapp.api.DisconnectReason
import it.auties.whatsapp.api.QrHandler
import it.auties.whatsapp.api.Whatsapp
import it.auties.whatsapp.model.chat.Chat
import it.auties.whatsapp.model.contact.ContactJid
import it.auties.whatsapp.model.info.MessageInfo
import it.auties.whatsapp.model.message.standard.TextMessage
import java.util.*
import kotlin.collections.HashMap
import kotlin.io.path.Path

fun main() {
	val testJid = ContactJid.of("120363024360186908@g.us")
	val qrPath = Path("C:\\Users\\eric0\\Desktop\\qr.png")

	val api = Whatsapp.webBuilder()
		.lastConnection()
		.name("padoru")
		.qrHandler(QrHandler.toFile(qrPath, QrHandler.ToFileConsumer.toDesktop()))
		.build()
		.addLoggedInListener { api: Whatsapp -> println("logged in: ${api.store().privacySettings()}") }
		.addDisconnectedListener { reason: DisconnectReason -> println("disconnect: $reason") }
		.addNewMessageListener { api: Whatsapp, messageInfo: MessageInfo ->
			run {
				println("received message\n${messageInfo.toJson()}")

				if (messageInfo.message().content() is TextMessage && messageInfo.senderJid().equals(ContactJid.of("393519808023@s.whatsapp.net")) && !(messageInfo.message().content() as TextMessage).text().lowercase(Locale.getDefault()).contains("gay")) {
					val textMessage = messageInfo.message().content() as TextMessage

					var text = textMessage.text().lowercase(Locale.getDefault());

					val changes = HashMap<String, String>()
					changes["man"] = "woman"
					changes["men"] = "women"
					changes["boy"] = "woman"
					changes["boys"] = "women"
					changes["boi"] = "gurl"
					changes["hate"] = "don't hate"
					for (kvp in changes) {
						text = text.replace(Regex("\\b${kvp.key}\\b"), kvp.value)
					}

					val badWords = HashMap<String, String>()
					badWords["cock"] = "[bad word]"
					badWords["cocks"] = "[bad word]"
					badWords["dick"] = "[bad word]"
					badWords["dicks"] = "[bad word]"
					badWords["penises"] = "[bad word]"
					for (kvp in badWords) {
						text = text.replace(kvp.key, kvp.value)
					}

					if (text == ":3") text = ":3 fuck the police :3"

					val message = TextMessage(text)
					api.sendMessage(messageInfo.chat(), message)
				}
			}
		}

	val thread = Thread {
		Thread.sleep(5000)
		val chat: Optional<Chat> = api.store().findChatByName("yoi")
		if (chat.isPresent)
			api.sendMessage(chat.get(), "meow")
		println("over")
	}

	api.connect().join()

	thread.start()
	thread.join()

	println("kill me")
}
