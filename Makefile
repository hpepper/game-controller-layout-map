.SUFFIXES : .png .xml
JAVA=java
MAP_DIR=maps

SRCS =  \
	${MAP_DIR}/007_qs.xml \
	${MAP_DIR}/ac4.xml \
	${MAP_DIR}/aot.xml \
	${MAP_DIR}/bf2_foot.xml \
	${MAP_DIR}/bf2_helicopter.xml \
	${MAP_DIR}/bf2_vehicle.xml \
	${MAP_DIR}/brutal_legend_foot.xml \
	${MAP_DIR}/brutal_legend_stage_battles.xml \
	${MAP_DIR}/brutal_legend_vehicle.xml \
	${MAP_DIR}/fme.xml \
	${MAP_DIR}/gow3.xml \
	${MAP_DIR}/graw.xml \
	${MAP_DIR}/halo3.xml \
	${MAP_DIR}/mw3.xml \
	${MAP_DIR}/pgr4.xml \
	${MAP_DIR}/titanfall_pilot.xml \
	${MAP_DIR}/titanfall_titan.xml \
	${MAP_DIR}/tron_cycle.xml \
	${MAP_DIR}/tron_foot.xml \
	${MAP_DIR}/tron_tank.xml

PICTS = ${SRCS:.xml=.png}

picts: $(PICTS)

clean:
		- rm $(MAP_DIR)/*.png

.xml.png :
			${JAVA} -jar ~/Downloads/XboxButtonMapping.jar -f $<
