package ir.gsce.emergency

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage
import android.util.Log

class SmsReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.provider.Telephony.SMS_RECEIVED") {
            val bundle = intent.extras
            if (bundle != null) {
                val pdus = bundle.get("pdus") as Array<*>?
                if (pdus != null) {
                    for (pdu in pdus) {
                        val format = bundle.getString("format")
                        val smsMessage = SmsMessage.createFromPdu(pdu as ByteArray, format)
                        val sender = smsMessage.originatingAddress
                        val messageBody = smsMessage.messageBody

                        val keywords = listOf("آدرس", "ادرس", "هستم")
                        
                        if (keywords.any { messageBody.contains(it) }) {
                            Log.d("SMS_RECEIVED", "تیکت جدید از: $sender - متن: $messageBody")
                        }
                    }
                }
            }
        }
    }
}
