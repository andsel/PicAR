package org.dna.picar

import com.sun.org.apache.bcel.internal.generic.StoreInstruction
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.web.servlet.support.RequestContextUtils
import grails.converters.JSON

class CardController {

    static final Set SUPPORTED_IMG_TYPES = ['png', 'gif', 'jpg', 'jpeg']

    def springSecurityService

    def imageService

    def exportingService

    def cardService

    def searchableService

    def grailsApplication

    static allowedMethods = [create: ['GET', 'POST'], edit: ['GET', 'POST'], delete: 'POST']

    def index() {
        redirect action: 'list', params: params
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [cardInstanceList: Card.list(params), cardInstanceTotal: Card.count()]
    }

    def upload(Long cardId) {
        //handle uploaded file
        def uploadedFile = request.getFile('file')
        if (uploadedFile.empty) {
            flash.error = message(code: 'filename.empty', default: 'Select a file to upload')
            redirect action: 'edit', id: cardId
            return
        }

        //check extension and mime type
        String fileExtension = uploadedFile.originalFilename[uploadedFile.originalFilename.lastIndexOf('.') + 1..-1]
        if (!fileExtension || fileExtension.empty || !SUPPORTED_IMG_TYPES.contains(fileExtension.toLowerCase())) {
            flash.error = message(code: 'filename.nameformat.error', default: 'Bad filename extension')
            redirect action: 'edit', id: cardId
            return
        }

        File destinationDir = getDestinationDir()
        //check not collision in filesystem
        File imageFile = new File(destinationDir, uploadedFile.originalFilename)
        if (imageFile.exists()) {
            flash.error = message(code: 'filename.already.present.error', default: 'A file with same name is already stored')
            redirect action: 'edit', id: cardId
            return
        }

        Card card = Card.get(cardId)
        if (!card) {
            flash.error = message(code: 'card.notfound.error', default: 'Card not found')
            redirect action: 'edit', id: cardId
            return
        }
        imageService.storeImageIntoCard(uploadedFile, imageFile, card)

        redirect action: 'edit', id: cardId
    }

    def retrieveImage(String file) {
        File destinationDir = getDestinationDir()

        def thumbFile = new File(destinationDir, file)
        response.setContentType("application/octet-stream")
        response.setHeader("Content-disposition", "attachment;filename=${thumbFile.getName()}")

        def memInputStream = new ByteArrayInputStream(thumbFile.bytes)
        response.outputStream << memInputStream
    }

    private File getDestinationDir() {
        def webRootDir
        def workdir = grailsApplication.config.workspace.dir
        if (workdir) {
            webRootDir = new File(workdir)
        } else {
            webRootDir = servletContext.getRealPath("/")
        }

//        def webRootDir = servletContext.getRealPath("/")
        def destinationDir = new File(webRootDir, 'imagestore')
        if (!destinationDir.exists()) {
            //create archive directory if not exists
            destinationDir.mkdir()
        }
        destinationDir
    }

    def create() {
		switch (request.method) {
		case 'GET':
        	[cardInstance: new Card(params)]
			break
		case 'POST':
	        def cardInstance = new Card(params)
            cardService.updatedCard(cardInstance)
            cardInstance.alignClosedProperties(request.locale)
            User currentUser = User.get(springSecurityService.principal.id)
            cardInstance.creator = currentUser

	        if (!cardInstance.save(flush: true)) {
	            render view: 'create', model: [cardInstance: cardInstance]
	            return
	        }

			flash.message = message(code: 'default.created.message', args: [message(code: 'card.label', default: 'Card'), cardInstance.id])
	        redirect action: 'edit', id: cardInstance.id
			break
		}
    }

    def show() {
        def cardInstance = Card.get(params.id)
        if (!cardInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'card.label', default: 'Card'), params.id])
            redirect action: 'list'
            return
        }

        [cardInstance: cardInstance]
    }


    def deleteImage(Long id) {
        StoredImage image = StoredImage.get(id)
        Card card = image.card
        File destinationDir = getDestinationDir()
        String err = imageService.deleteImage(image, destinationDir)
        if (err) {
            flash.error = message(code: err)
            redirect action: 'edit', id: card.id
            return
        }

        redirect action: 'edit', id: card.id
    }


    /**
     * Return the original file containing the image
     * */
    def originalImage(Long id) {
        StoredImage image = StoredImage.get(id)

        File imageFile = new File(destinationDir, image.completeName)

        byte[] fileBytes = imageFile.bytes
        response.setContentLength(fileBytes.length)
        response.contentType = "image/${image.extension}"
        response.setHeader("Content-disposition", "attachment;filename=" + image.completeName)
        response.outputStream.write(fileBytes)
        response.outputStream.flush()
    }

    def edit() {
		switch (request.method) {
		case 'GET':
	        def cardInstance = Card.get(params.id)
	        if (!cardInstance) {
	            flash.message = message(code: 'default.not.found.message', args: [message(code: 'card.label', default: 'Card'), params.id])
	            redirect action: 'list'
	            return
	        }

	        [cardInstance: cardInstance]
			break
		case 'POST':
	        def cardInstance = Card.get(params.id)
	        if (!cardInstance) {
	            flash.message = message(code: 'default.not.found.message', args: [message(code: 'card.label', default: 'Card'), params.id])
	            redirect action: 'list'
	            return
	        }

	        if (params.version) {
	            def version = params.version.toLong()
	            if (cardInstance.version > version) {
	                cardInstance.errors.rejectValue('version', 'default.optimistic.locking.failure',
	                          [message(code: 'card.label', default: 'Card')] as Object[],
	                          "Another user has updated this Card while you were editing")
	                render view: 'edit', model: [cardInstance: cardInstance]
	                return
	            }
	        }

	        cardInstance.properties = params
            cardInstance.alignClosedProperties(request.locale)

	        if (!cardInstance.save(flush: true)) {
	            render view: 'edit', model: [cardInstance: cardInstance]
	            return
	        }

            cardService.updatedCard(cardInstance)
//            searchableService.reindex(cardInstance)

			flash.message = message(code: 'default.updated.message', args: [message(code: 'card.label', default: 'Card'), cardInstance.id])
	        redirect action: 'show', id: cardInstance.id
			break
		}
    }

    def delete() {
        def cardInstance = Card.get(params.id)
        if (!cardInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'card.label', default: 'Card'), params.id])
            redirect action: 'list'
            return
        }

        try {
            //delete also stored images
            File destinationDir = getDestinationDir()

            List<StoredImage> images = cardInstance.images.collect()
            for (StoredImage image : images) {
                String err = imageService.deleteImage(image, destinationDir)
                cardInstance.errors.rejectValue('images', 'default.optimistic.locking.failure',
                        [message(code: 'card.label', default: 'Card')] as Object[],
                        err)
            }

            cardInstance.delete(flush: true)

			flash.message = message(code: 'default.deleted.message', args: [message(code: 'card.label', default: 'Card'), params.id])
            redirect action: 'list'
        } catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'card.label', default: 'Card'), params.id])
            redirect action: 'show', id: params.id
        }
    }

    def exportCard(Long id) {
        File destinationDir = getDestinationDir()
        def card = Card.get(id)
        def locale = RequestContextUtils.getLocale(request)
        ByteArrayOutputStream mempdf = exportingService.generateCardDoc(card, destinationDir, locale)
        response.setContentType("application/pdf")
        response.setContentLength(mempdf.size())

        OutputStream os = response.outputStream
        mempdf.writeTo(os)
        os.flush()
        os.close()
    }

    def exportCatalog() {
        File destinationDir = getDestinationDir()
        def locale = RequestContextUtils.getLocale(request)
        ByteArrayOutputStream mempdf = exportingService.generateCatalogDoc(destinationDir, locale)
        response.setContentType("application/pdf")
        response.setContentLength(mempdf.size())

        OutputStream os = response.outputStream
        mempdf.writeTo(os)
        os.flush()
        os.close()
    }

    /**
     * Ajax call for typeahead completion
     * */
    def autoCompletion(String field, String query) {
        log.debug "autoCompletion $params"

        Set selecteds = cardService.searchMatching(field, query)

        Map resp = ["options": selecteds]
        render resp as JSON
    }

    /**
     * Used by the admin to force the reindexing of the entire cards DB.
     * */
    def reindex() {
        log.info 'Invoked reindex, should be done only by Admin'
        Card.reindex()
        log.info 'Finished to reindex'
        redirect controller: 'user', action: 'list'
    }
 }
