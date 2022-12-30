package example.micronaut

import io.micronaut.context.ApplicationContext
import io.micronaut.context.env.Environment
import io.micronaut.runtime.Micronaut.*


//@ContextConfigurer
//class DefaultEnvironmentConfigurer : ApplicationContextConfigurer {
//	override fun configure(@NonNull builder: ApplicationContextBuilder) {
//		println("DEV!");
//		builder.defaultEnvironments("dev")
//	}
//}
//val applicationContext = ApplicationContext.run(droid")
//val environment = applicationContext.environment

fun main(args: Array<String>) {
//	ApplicationContext.run("dev")
	run(*args)
}


//	fun main(args: Array<String>) {
//		build(*args)
//	.defaultEnvironments(Environment.DEVELOPMENT)
//			.start()
//	}
//
