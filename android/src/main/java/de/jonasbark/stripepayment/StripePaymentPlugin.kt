package de.jonasbark.stripepayment

import android.accounts.Account
import android.app.Activity
import android.content.Intent
import androidx.fragment.app.FragmentActivity
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReadableMap
import com.gettipsi.stripe.StripeModule
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.wallet.*
import com.stripe.android.*
import com.stripe.android.model.*
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry
import io.flutter.plugin.common.PluginRegistry.Registrar
import org.json.JSONObject
import java.text.Normalizer
import java.util.HashMap

class StripePaymentPlugin(private val stripeModule: StripeModule) : MethodCallHandler {

    override fun onMethodCall(call: MethodCall, result: Result) {
        when (call.method) {
            "setOptions" -> stripeModule.init(ReadableMap(call.arguments as Map<String, Any>), ReadableMap())
            "deviceSupportsAndroidPay" -> stripeModule.deviceSupportsAndroidPay(Promise(result));
            "paymentRequestWithAndroidPay" -> stripeModule.paymentRequestWithAndroidPay(
                ReadableMap(call.arguments as Map<String, Any>),
                Promise(result)
            )
            "paymentRequestWithCardForm" -> stripeModule.paymentRequestWithCardForm(
                ReadableMap(call.arguments as Map<String, Any>),
                Promise(result)
            )
            "createTokenWithCard" -> stripeModule.createTokenWithCard(
                ReadableMap(call.arguments as Map<String, Any>),
                Promise(result)
            )
            "createTokenWithBankAccount" -> stripeModule.createTokenWithBankAccount(
                ReadableMap(call.arguments as Map<String, Any>),
                Promise(result)
            )
            "createSourceWithParams" -> stripeModule.createSourceWithParams(
                ReadableMap(call.arguments as Map<String, Any>),
                Promise(result)
            )
        }
    }

    companion object {

        @JvmStatic
        fun registerWith(registrar: Registrar) {
            val channel = MethodChannel(registrar.messenger(), "stripe_payment")
            val stripeModule = StripeModule(registrar, registrar.activity() as FragmentActivity)
            val plugin = StripePaymentPlugin(stripeModule)
            channel.setMethodCallHandler(plugin)
        }
    }
}
