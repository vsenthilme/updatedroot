CREATE OR ALTER PROCEDURE imbasicdata_description_update_proc
		
AS
BEGIN

UPDATE TH SET TH.itm_typ_text = X.itm_typ FROM tblimbasicdata1 TH INNER JOIN 
(SELECT c_id,plant_id, lang_id, wh_id, itm_type_id, itm_typ FROM tblitemtypeid) X ON 
X.c_id = TH.c_id and x.plant_id = th.plant_id and x.lang_id = th.lang_id and x.wh_id = th.wh_id 
and x.itm_type_id = th.itm_typ_id and th.itm_typ_text is null and th.is_deleted = 0

UPDATE TH SET TH.itm_grp_text = X.imt_grp FROM tblimbasicdata1 TH INNER JOIN 
(SELECT c_id,plant_id, lang_id, wh_id, itm_grp_id, imt_grp FROM tblitemgroupid) X ON 
X.c_id = TH.c_id and x.plant_id = th.plant_id and x.lang_id = th.lang_id and x.wh_id = th.wh_id 
and x.itm_grp_id = th.itm_grp_id and th.itm_grp_text is null and th.is_deleted = 0

UPDATE TH SET TH.wh_text = X.wh_text FROM tblimbasicdata1 TH INNER JOIN 
(SELECT c_id,plant_id, lang_id, wh_id, wh_text FROM tblwarehouseid) X ON 
X.c_id = TH.c_id and x.plant_id = th.plant_id and x.lang_id = th.lang_id and x.wh_id = th.wh_id
and th.wh_text is null and th.is_deleted = 0

UPDATE TH SET TH.plant_text = X.plant_text FROM tblimbasicdata1 TH INNER JOIN 
(SELECT c_id,plant_id, lang_id, plant_text FROM tblplantid) X ON 
X.c_id = TH.c_id and x.plant_id = th.plant_id and x.lang_id = th.lang_id
and th.plant_text is null and th.is_deleted = 0

UPDATE TH SET TH.c_text = X.c_text FROM tblimbasicdata1 TH INNER JOIN 
(SELECT c_id, lang_id, c_text FROM tblcompanyid) X ON 
X.c_id = TH.c_id and x.lang_id = th.lang_id and th.c_text is null and th.is_deleted = 0

END