CI_BUILD_NUMBER ?= $(USER)-SNAPSHOT
CI_IVY_CACHE ?= $(HOME)/.ivy2
CI_SBT_CACHE ?= $(HOME)/.sbt
CI_WORKDIR ?= $(shell pwd)

TARGET ?= __package-sbt

VERSION ?= 0.0.$(CI_BUILD_NUMBER)
BUILDER_TAG = "meetup/sbt-builder:0.1.5"

# lists all available targets
list:
	@sh -c "$(MAKE) -p no_op__ | \
		awk -F':' '/^[a-zA-Z0-9][^\$$#\/\\t=]*:([^=]|$$)/ {split(\$$1,A,/ /);\
		for(i in A)print A[i]}' | \
		grep -v '__\$$' | \
		grep -v 'make\[1\]' | \
		grep -v 'Makefile' | \
		sort"

# required for list
no_op__:

__package-sbt:
	sbt clean \
		coverage \
		test \
		coverageReport \
		coverallsMaybe \
		coverageOff \
		publishLocal \
		component:test

__publish-sbt: __package-sbt
	sbt publish cleanLocal

__set-publish:
	$(eval TARGET=__publish-sbt)

__contained-target:
	docker run \
		--rm \
		-v $(CI_WORKDIR):/data \
		-v $(CI_IVY_CACHE):/root/.ivy2 \
		-v $(CI_SBT_CACHE):/root/.sbt \
		-v $(HOME)/.bintray:/root/.bintray \
		-e CI_BUILD_NUMBER=$(CI_BUILD_NUMBER) \
		-e TRAVIS_JOB_ID=$(TRAVIS_JOB_ID) \
		-e TRAVIS_PULL_REQUEST=$(TRAVIS_PULL_REQUEST) \
		$(BUILDER_TAG) \
		make $(TARGET)

package: __contained-target

publish: __set-publish __contained-target

version:
	@echo $(VERSION)

