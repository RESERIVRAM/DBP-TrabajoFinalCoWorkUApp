package com.example.coworku.data.repository.fake

import com.example.coworku.R
import com.example.coworku.domain.model.FaqItem
import com.example.coworku.domain.model.ForumCategory
import com.example.coworku.domain.model.ForumPost
import com.example.coworku.domain.model.ForumThread
import com.example.coworku.domain.model.NewsItem

class CommunityRepositoryFake {

    fun getForumCategories(): List<ForumCategory> {
        return listOf(
            ForumCategory("cat1", "Anuncios de CoWorkU", "Novedades y noticias del equipo.", R.drawable.ic_launcher_background),
            ForumCategory("cat2", "Presenta tu proyecto", "Muestra en lo que estás trabajando.", R.drawable.ic_launcher_background),
            ForumCategory("cat3", "Busca equipo o miembros", "Encuentra colaboradores.", R.drawable.ic_launcher_background),
            ForumCategory("cat4", "Tecnologías y herramientas", "Debates sobre el stack.", R.drawable.ic_launcher_background),
            ForumCategory("cat5", "Eventos y hackathons", "Competencias y encuentros.", R.drawable.ic_launcher_background),
            ForumCategory("cat6", "Consejos de trabajo en equipo", "Mejora la colaboración.", R.drawable.ic_launcher_background)
        )
    }

    fun getForumThreads(): List<ForumThread> {
        return listOf(
            ForumThread(
                id = "thread1",
                categoryId = "cat3",
                title = "Buscamos diseñador UX/UI para app móvil educativa",
                authorName = "Laura Pérez",
                authorRole = "Estudiante de Diseño Gráfico",
                excerpt = "Somos un equipo de 3 estudiantes de ingeniería y necesitamos un diseñador para un proyecto de app de aprendizaje de idiomas. Usamos Jetpack Compose y el diseño debe ser minimalista.",
                createdAt = "hace 2 horas",
                repliesCount = 5,
                isPinned = true,
                tags = listOf("Diseño UX/UI", "Android", "Educación"),
                lastActivity = "hace 15 minutos"
            ),
            ForumThread(
                id = "thread2",
                categoryId = "cat4",
                title = "Cómo organizar un backlog ágil en proyectos universitarios",
                authorName = "Carlos Ruiz",
                authorRole = "Estudiante de Ing. de Software",
                excerpt = "He probado Trello y Jira, pero me gustaría saber qué herramientas usan para gestionar tareas en equipos pequeños y para proyectos de 1-2 semestres. ¿Alguna recomendación?",
                createdAt = "hace 1 día",
                repliesCount = 12,
                isPinned = false,
                tags = listOf("Metodologías Ágiles", "Gestión de Proyectos"),
                lastActivity = "hace 1 hora"
            ),
             ForumThread(
                id = "thread3",
                categoryId = "cat4",
                title = "Usando Jetpack Compose en trabajos de la universidad: buenas prácticas",
                authorName = "Ana Gómez",
                authorRole = "Desarrolladora Android",
                excerpt = "Abro este hilo para compartir consejos y buenas prácticas sobre Jetpack Compose en nuestros proyectos. ¿Cómo manejan el estado? ¿Qué librerías de navegación usan?",
                createdAt = "hace 5 horas",
                repliesCount = 8,
                isPinned = true,
                tags = listOf("Jetpack Compose", "Android", "Kotlin"),
                lastActivity = "hace 30 minutos"
            ),
            ForumThread(
                id = "thread4",
                categoryId = "cat1",
                title = "Equipo CoWorkU: cambios en las reglas para publicar proyectos",
                authorName = "Equipo CoWorkU",
                authorRole = "Administrador",
                excerpt = "Hemos actualizado nuestras directrices para la publicación de proyectos para asegurar la calidad y relevancia de las propuestas. Por favor, revisen las nuevas normas.",
                createdAt = "hace 3 días",
                repliesCount = 2,
                isPinned = true,
                tags = listOf("Anuncios", "Reglas"),
                lastActivity = "hace 1 día"
            ),
            ForumThread(
                id = "thread5",
                categoryId = "cat5",
                title = "Concurso interno: comparte tu proyecto final y gana mentorías",
                authorName = "Equipo CoWorkU",
                authorRole = "Administrador",
                excerpt = "¡Muestra tu proyecto final de semestre! Los 3 mejores proyectos recibirán sesiones de mentoría con profesionales de la industria. Fecha límite: 30 de julio.",
                createdAt = "hace 1 semana",
                repliesCount = 25,
                isPinned = false,
                tags = listOf("Concurso", "Mentorías", "Proyectos"),
                lastActivity = "hace 2 horas"
            )
        )
    }

    fun getForumPosts(threadId: String): List<ForumPost> {
        val posts = listOf(
             ForumPost("post1", "thread1", "Laura Pérez", "Estudiante de Diseño Gráfico", "Hola a todos, como mencioné, buscamos un diseñador. El proyecto es una app para aprender vocabulario de forma interactiva. ¡Espero sus mensajes!", "hace 2 horas", true),
             ForumPost("post2", "thread1", "Juan García", "Desarrollador Android", "¡Hola Laura! Suena interesante. ¿Tienen algún prototipo o wireframe inicial? Me gustaría ver el alcance del proyecto.", "hace 1 hora", false),
             ForumPost("post3", "thread1", "Laura Pérez", "Estudiante de Diseño Gráfico", "Hola Juan, sí, tenemos unos bocetos básicos. Te los envío por mensaje privado.", "hace 45 minutos", true),
             ForumPost("post4", "thread2", "Carlos Ruiz", "Estudiante de Ing. de Software", "Gracias por abrir este hilo. En mi equipo usamos Notion, es flexible y gratis para estudiantes.", "hace 20 horas", true),
             ForumPost("post5", "thread2", "Maria Rodriguez", "Project Manager Jr.", "Nosotros usamos Asana. La versión gratuita es bastante completa para equipos pequeños. La vista de cronograma ayuda mucho.", "hace 18 horas", false)
        )
        return posts.filter { it.threadId == threadId }
    }

    fun getNewsItems(): List<NewsItem> {
        return listOf(
            NewsItem(
                id = "news1",
                title = "Nueva función: filtros de habilidad mejorados",
                subtitle = "Encuentra a tus colaboradores ideales más rápido que nunca.",
                contentPreview = "Hemos lanzado una actualización que te permite filtrar proyectos y perfiles por habilidades específicas, nivel de experiencia y más.",
                fullContent = "Descripción completa de la nueva funcionalidad...",
                publishedAt = "hace 2 días",
                tag = "Novedad"
            ),
            NewsItem(
                id = "news2",
                title = "Evento de networking online para estudiantes de tech",
                subtitle = "Conecta con futuros colegas y mentores.",
                contentPreview = "Únete a nuestro evento virtual el próximo 15 de agosto. Habrá charlas, sesiones de networking y oportunidades para presentar tu proyecto.",
                fullContent = "Más detalles sobre el evento online...",
                publishedAt = "hace 5 días",
                tag = "Evento"
            ),
            NewsItem(
                id = "news3",
                title = "Historia de éxito: equipo de 'EcoScan' gana hackathon",
                subtitle = "Se conocieron en CoWorkU y crearon una app para reciclaje.",
                contentPreview = "El equipo detrás de 'EcoScan', una app que identifica tipos de plástico mediante la cámara del móvil, se formó en nuestra plataforma y ganó el primer lugar en el Hackathon 'Green Tech'.",
                fullContent = "Entrevista con los miembros del equipo y cómo CoWorkU les ayudó a conectar...",
                publishedAt = "hace 1 semana",
                tag = "Historia de éxito"
            )
        )
    }

    fun getFaqItems(): List<FaqItem> {
        return listOf(
            FaqItem("faq1", "¿Cómo creo un nuevo proyecto en CoWorkU?", "Para crear un proyecto, ve a la sección 'Mis Proyectos' y pulsa el botón '+'. Rellena el formulario con el nombre, la descripción, las tecnologías y los roles que buscas.", "Proyectos y publicaciones"),
            FaqItem("faq2", "¿Cómo puedo buscar compañeros con ciertas habilidades?", "Usa la barra de búsqueda en la pantalla principal y aplica los filtros de habilidad y rol. También puedes explorar perfiles de usuario directamente.", "Equipos y colaboración"),
            FaqItem("faq3", "¿Qué debo tener en cuenta antes de aceptar a alguien en mi equipo?", "Recomendamos revisar su perfil, proyectos anteriores y tener una breve charla para asegurar que sus expectativas y habilidades encajan con las del proyecto.", "Equipos y colaboración"),
            FaqItem("faq4", "¿Puedo reportar contenido inapropiado en la comunidad?", "Sí. En cada publicación o perfil encontrarás un menú de opciones (tres puntos) con la opción 'Reportar'. Nuestro equipo de moderación lo revisará.", "Comunidad y foros"),
            FaqItem("faq5", "¿Cómo protege CoWorkU mis datos personales?", "Nos tomamos la privacidad muy en serio. Puedes leer nuestra política de privacidad completa en los ajustes de tu perfil. No compartimos tus datos con terceros sin tu consentimiento.", "Privacidad y seguridad"),
            FaqItem("faq6", "¿Qué hago si la app no carga o tengo errores técnicos?", "Intenta cerrar y volver a abrir la app. Si el problema persiste, ve a 'Centro de Ayuda' y usa la opción 'Escribir al soporte' para contactarnos.", "Soporte técnico")
        )
    }
}
