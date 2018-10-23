import { Route } from '@angular/router';

import { AudMetricsMonitoringComponent } from './metrics.component';

export const metricsRoute: Route = {
    path: 'aud-metrics',
    component: AudMetricsMonitoringComponent,
    data: {
        pageTitle: 'metrics.title'
    }
};
