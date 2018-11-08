import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';
import { JacksonviewsSharedModule } from 'app/shared';
/* jhipster-needle-add-admin-module-import - JHipster will add admin modules imports here */

import {
    adminState,
    AuditsComponent,
    UserMgmtComponent,
    UserMgmtDetailComponent,
    UserMgmtUpdateComponent,
    UserMgmtDeleteDialogComponent,
    LogsComponent,
    JacMetricsMonitoringModalComponent,
    JacMetricsMonitoringComponent,
    JacHealthModalComponent,
    JacHealthCheckComponent,
    JacConfigurationComponent,
    JacDocsComponent
} from './';

@NgModule({
    imports: [
        JacksonviewsSharedModule,
        RouterModule.forChild(adminState)
        /* jhipster-needle-add-admin-module - JHipster will add admin modules here */
    ],
    declarations: [
        AuditsComponent,
        UserMgmtComponent,
        UserMgmtDetailComponent,
        UserMgmtUpdateComponent,
        UserMgmtDeleteDialogComponent,
        LogsComponent,
        JacConfigurationComponent,
        JacHealthCheckComponent,
        JacHealthModalComponent,
        JacDocsComponent,
        JacMetricsMonitoringComponent,
        JacMetricsMonitoringModalComponent
    ],
    entryComponents: [UserMgmtDeleteDialogComponent, JacHealthModalComponent, JacMetricsMonitoringModalComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JacksonviewsAdminModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
