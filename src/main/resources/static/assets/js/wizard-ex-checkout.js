"use strict";
$(function() {
    var e = $(".read-only-ratings");
    e && e.rateYo({
        rtl: isRtl,
        rating: 4,
        starWidth: "20px"
    })
}),
    function() {
        window.Helpers.initCustomOptionCheck();
        var e = document.querySelector(".credit-card-mask")
            , o = document.querySelector(".expiry-date-mask")
            , i = document.querySelector(".cvv-code-mask")
            , e = (e && new Cleave(e,{
            creditCard: !0,
            onCreditCardTypeChanged: function(e) {
                document.querySelector(".card-type").innerHTML = "" != e && "unknown" != e ? '<img src="' + assetsPath + "img/icons/payments/" + e + '-cc.png" height="28"/>' : ""
            }
        }),
        o && new Cleave(o,{
            date: !0,
            delimiter: "/",
            datePattern: ["m", "y"]
        }),
        i && new Cleave(i,{
            numeral: !0,
            numeralPositiveOnly: !0
        }),
            document.querySelector("#wizard-checkout"));
        if (null !== e) {
            var o = e.querySelector("#wizard-checkout-form")
                , i = o.querySelector("#checkout-cart")
                , n = o.querySelector("#checkout-address")
                , a = o.querySelector("#checkout-payment")
                , r = o.querySelector("#checkout-confirmation")
                , l = [].slice.call(o.querySelectorAll(".btn-next"))
                , o = [].slice.call(o.querySelectorAll(".btn-prev"));
            let t = new Stepper(e,{
                linear: !1
            });
            const u = FormValidation.formValidation(i, {
                fields: {},
                plugins: {
                    trigger: new FormValidation.plugins.Trigger,
                    bootstrap5: new FormValidation.plugins.Bootstrap5({
                        eleValidClass: ""
                    }),
                    autoFocus: new FormValidation.plugins.AutoFocus,
                    submitButton: new FormValidation.plugins.SubmitButton
                }
            }).on("core.form.valid", function() {
                t.next()
            })
                , c = FormValidation.formValidation(n, {
                fields: {},
                plugins: {
                    trigger: new FormValidation.plugins.Trigger,
                    bootstrap5: new FormValidation.plugins.Bootstrap5({
                        eleValidClass: ""
                    }),
                    autoFocus: new FormValidation.plugins.AutoFocus,
                    submitButton: new FormValidation.plugins.SubmitButton
                }
            }).on("core.form.valid", function() {
                t.next()
            })
                , s = FormValidation.formValidation(a, {
                fields: {},
                plugins: {
                    trigger: new FormValidation.plugins.Trigger,
                    bootstrap5: new FormValidation.plugins.Bootstrap5({
                        eleValidClass: ""
                    }),
                    autoFocus: new FormValidation.plugins.AutoFocus,
                    submitButton: new FormValidation.plugins.SubmitButton
                }
            }).on("core.form.valid", function() {
                t.next()
            })
                , d = FormValidation.formValidation(r, {
                fields: {},
                plugins: {
                    trigger: new FormValidation.plugins.Trigger,
                    bootstrap5: new FormValidation.plugins.Bootstrap5({
                        eleValidClass: "",
                        rowSelector: ".col-md-12"
                    }),
                    autoFocus: new FormValidation.plugins.AutoFocus,
                    submitButton: new FormValidation.plugins.SubmitButton
                }
            }).on("core.form.valid", function() {
                alert("Submitted..!!")
            });
            l.forEach(e=>{
                    e.addEventListener("click", e=>{
                            switch (t._currentIndex) {
                                case 0:
                                    u.validate();
                                    break;
                                case 1:
                                    c.validate();
                                    break;
                                case 2:
                                    s.validate();
                                    break;
                                case 3:
                                    d.validate()
                            }
                        }
                    )
                }
            ),
                o.forEach(e=>{
                        e.addEventListener("click", e=>{
                                switch (t._currentIndex) {
                                    case 3:
                                    case 2:
                                    case 1:
                                        t.previous()
                                }
                            }
                        )
                    }
                )
        }
    }();
